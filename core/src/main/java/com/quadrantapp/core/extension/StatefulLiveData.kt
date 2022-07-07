package com.quadrantapp.core.extension

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.quadrantapp.core.base.BaseUseCase
import com.quadrantapp.core.model.Error
import kotlinx.coroutines.CoroutineScope

class StatefulLiveData<P, R>(
    private val executor: BaseUseCase<P, R>,
    private val coroutineScope: CoroutineScope,
    private val isNeedTrigger:Boolean = false
) {
    var idx = 0

    val value: R get() = onSuccess.value ?: executor.default
    val liveValue: LiveData<R> get() = onSuccess
    val statefulResultLiveData: MediatorLiveData<StatefulResult<R>> = MediatorLiveData()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val executable = MediatorLiveData<P>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val onSuccess = MediatorLiveData<R>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val onError = MediatorLiveData<Error>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val isLoading = MediatorLiveData<Boolean>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val isTriggered = MediatorLiveData<Boolean>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val onCanceled = MediatorLiveData<Unit>()
    var onTokenExpired: () -> Unit = {}

    private var isExecuted = false

    fun get(param: P, isSingleRequest: Boolean = false) {
        if (!isSingleRequest || !isExecuted) {
            isTriggered.postValue(true)
            isLoading.postValue(true)
            executable.postValue(param)
            isExecuted = true
        }
    }

    fun refresh() {
        if (isExecuted) {
            isTriggered.postValue(true)
            isLoading.postValue(true)
            executable.postValue(executable.value)
        }
    }

    fun cancel() {
        executor.cancel()
        onCanceled.postValue(Unit)
        isTriggered.postValue(false)
        isLoading.postValue(false)
    }

    fun renew(): StatefulLiveData<P, R> {
        return StatefulLiveData(
            executor,
            coroutineScope
        )
    }

    fun listen(
        owner: LifecycleOwner,
        onSuccess: ((R) -> Unit)? = null,
        onError: ((Error) -> Unit)? = null,
        onCanceled: (() -> Unit)? = null,
        onStart: (() -> Unit)? = null,
        onComplete: (() -> Unit)? = null
    ) {
        this.onSuccess.observe(owner, Observer { if (onSuccess != null && (!isNeedTrigger || isTriggered.value == true)) onSuccess(it) })
        // onError and onCanceled don't need isNeedTrigger checking
        // because we don't want to show error message repeatedly even if isNeedTrigger is true
        this.onError.observe(owner, Observer { if (onError != null && isTriggered.value==true) onError(it) })
        this.onCanceled.observe(owner, Observer { if (onCanceled != null && isTriggered.value==true) onCanceled() })

        this.isLoading.observe(owner, Observer {
            if (it == true && onStart != null) onStart()
            if (it == false && isTriggered.value==true) {
                isTriggered.postValue(false)
                onComplete?.invoke()
            }
        })
    }

    private val execution: LiveData<StatefulResult<R>>
        get() = executionMutable

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val executionMutable: MediatorLiveData<StatefulResult<R>> = MediatorLiveData()

    init {
        onSuccess.addSource(execution) {
            if (it is StatefulResult.Success) {
                onSuccess.postValue(it.data)
                isLoading.postValue(false)
                statefulResultLiveData.postValue(it)
            }
        }

        onError.addSource(execution) {
            if (it is StatefulResult.Failed) {
                onError.postValue(it.error)
                isLoading.postValue(false)
                statefulResultLiveData.postValue(it)
            }
        }

        executionMutable.addSource(executable) {
            executor.execute(coroutineScope, it, executionMutable::postValue)
        }
    }
}
