package com.quadrantapp.core.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.quadrantapp.core.extension.StatefulLiveData
import com.quadrantapp.core.model.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    override fun onCleared() {
        val killables = getKillableStatefulLiveData()
        killables.forEach {
            it.cancel()
        }

        super.onCleared()
    }

    val isAuthenticationError = MutableLiveData<Boolean>()

    suspend fun <T> Flow<T>.safelyCollect(onError: (Error?) -> Unit) {
        catch { exception ->
            if (exception is FlowFailedException) {
                if (exception.caused?.code == Error.UNAUTHORIZED) {
                    isAuthenticationError.value = true
                } else {
                    onError(exception.caused)
                }
            } else {
                throw exception
            }
        }.collect()
    }

    abstract fun getKillableStatefulLiveData(): List<StatefulLiveData<*, *>>
}