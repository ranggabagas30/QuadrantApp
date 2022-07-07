package com.quadrantapp.core.base

import androidx.annotation.VisibleForTesting
import com.quadrantapp.core.extension.StatefulResult
import com.quadrantapp.core.extension.either
import com.quadrantapp.core.extension.toResult
import com.quadrantapp.core.model.Result
import kotlinx.coroutines.*

abstract class BaseUseCase<P, R> {
    abstract val default: R
    private var job: Job? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    abstract suspend fun build(param: P): Result<R>

    fun execute(coroutineScope: CoroutineScope, param: P, onResult: (StatefulResult<R>) -> Unit) {
        job = coroutineScope.launch(Dispatchers.Default) {
            onResult(
                either {
                    withContext(Dispatchers.IO) {
                        build(param)
                    }
                }.toResult()
            )
        }
    }

    fun cancel() {
        job?.cancel()
    }
}