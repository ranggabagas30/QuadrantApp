package com.quadrantapp.core.extension

import com.quadrantapp.core.model.Error

fun <P, R> StatefulLiveData<P, R>.mockSuccess(result: R) {
    this.isTriggered.postValue(true)
    this.executionMutable.postValue(
        StatefulResult.Success(result)
    )
}

fun <P, R> StatefulLiveData<P, R>.mockError(error: Error) {
    this.isTriggered.postValue(true)
    this.executionMutable.postValue(
        StatefulResult.Failed(
            error
        )
    )
}