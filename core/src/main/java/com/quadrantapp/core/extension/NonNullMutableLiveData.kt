package com.quadrantapp.core.extension

import androidx.lifecycle.MutableLiveData

class NonNullMutableLiveData<T: Any>(
    private val initialValue: T
) : MutableLiveData<T>(initialValue) {

    override fun getValue(): T = super.getValue() ?: initialValue
}