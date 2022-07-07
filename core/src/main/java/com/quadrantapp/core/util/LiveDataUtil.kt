package com.quadrantapp.core.util

import com.quadrantapp.core.extension.NonNullMutableLiveData

object LiveDataUtil {
    /**
     * List operations
     * */
    fun <T> NonNullMutableLiveData<out MutableCollection<T>>.clearAll() {
        this.value.clear()
    }

    fun <T> NonNullMutableLiveData<out MutableCollection<T>>.addAll(items: Collection<T>) {
        val list = this.value
        list.addAll(items)
        this.value = list
    }

    fun <T> NonNullMutableLiveData<out MutableCollection<T>>.add(item: T) {
        val list = this.value
        list.add(item)
        this.value = list
    }

    fun <T> NonNullMutableLiveData<out MutableList<T>>.add(index: Int, item: T) {
        val list = this.value
        list.add(index, item)
        this.value = list
    }

    fun <T> NonNullMutableLiveData<out MutableList<T>>.set(index: Int, item: T) {
        val list = this.value
        list[index] = item
        this.value = list
    }

    fun <T> NonNullMutableLiveData<out MutableCollection<T>>.remove(item: T): Boolean {
        return try {
            val list = this.value
            val isRemoved = list.remove(item)
            this.value = list
            isRemoved
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun <T> NonNullMutableLiveData<out MutableList<T>>.removeAt(index: Int) {
        try {
            val list = this.value
            list.removeAt(index)
            this.value = list
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}