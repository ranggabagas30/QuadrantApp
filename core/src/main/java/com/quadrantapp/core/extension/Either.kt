package com.quadrantapp.core.extension

import com.google.android.gms.common.api.ApiException
import com.quadrantapp.core.BuildConfig
import com.quadrantapp.core.model.Error
import com.quadrantapp.core.model.Result
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <V> either(block: suspend () -> Result<V>): StatefulResponse<V> = runCatching {
    StatefulResponse.Success(block())
}.getOrElse {
    StatefulResponse.Error(it)
}

fun <T> StatefulResponse<T>.toResult(): StatefulResult<T> = when {
    this is StatefulResponse.Error -> StatefulResult.Failed(this.exception.toError())
    this is StatefulResponse.Success && this.data.data == null && this.data.message != null -> StatefulResult.Failed(
        Error(this.data.code, this.data.message)
    )
    this is StatefulResponse.Success && this.data.data != null -> StatefulResult.Success(this.data.data)
    else -> StatefulResult.Failed(Error(Error.NO_DATA, "No Data"))
}

fun Throwable.toError(): Error {
    if (BuildConfig.DEBUG) {
        printStackTrace()
    }
    return when (this) {
        is SocketTimeoutException, is InterruptedIOException, is UnknownHostException, is ConnectException -> Error(Error.NO_INTERNET, this.message?: this::class.java.simpleName)
        is ApiException -> Error(this.statusCode.toString(), this.message ?: this::class.java.simpleName)
        is Exception -> Error("", this.localizedMessage ?: this::class.java.simpleName)
        else -> Error("", this.message ?: this::class.java.simpleName)
    }
}