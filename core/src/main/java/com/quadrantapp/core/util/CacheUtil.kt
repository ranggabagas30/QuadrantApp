package com.quadrantapp.core.util

import android.content.Context
import android.content.SharedPreferences
import timber.log.Timber

object CacheUtil {

    private val TAG = this::class.java.simpleName
    private const val configName = "QUADRANT_SHARED_PREFERENCE"

    fun <T> set(context: Context, key: String, value: T) {
        val sharedPref = getPreference(context)

        with(sharedPref.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value as Boolean)
                is String -> putString(key, value as String)
                is Int -> putInt(key,value as Int)
                is Long -> putLong(key,value as Long)
            }

            commit()
        }
    }

    fun <T> get(context: Context, key: String, default: T): T {
        val sharedPref = getPreference(context)

        return when (default) {
            is Boolean -> sharedPref.getBoolean(key, default) as T
            is String -> sharedPref.getString(key, default) as T
            is Int -> sharedPref.getInt(key,default) as T
            is Long -> sharedPref.getLong(key,default) as T

            else -> default
        }
    }

    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(configName, Context.MODE_PRIVATE)
    }

    fun clear(context: Context): Boolean {
        val sharedPref = getPreference(context)
        return sharedPref.edit().clear().commit()
    }

    fun remove(context: Context, key: String) {
        val sharedPref = getPreference(context)
        sharedPref.edit().remove(key).apply()
    }

    fun clearCacheByKey(context: Context, url: String, vararg requests: String = arrayOf()) {
        val sharedPref = getPreference(context)
        for (mutableEntry in sharedPref.all) {
            if(mutableEntry.key.contains(url)) {
                val keys = mutableEntry.key.split(";")
                val keyUrl = keys[0]
                val keyRequest = keys[1]
                if (keyUrl == url) {
                    requests.forEach {
                        if (!keyRequest.contains(it))
                        {
                            Timber.d(TAG, "key to be deleted not found")
                            return
                        }
                    }
                    Timber.d(TAG, "key to be deleted: ${mutableEntry.key}")
                    remove(context, mutableEntry.key)
                }
            }
        }
        return
    }

    fun getAll(context: Context): MutableMap<String, *>? {
        val sharedPref = getPreference(context)
        return sharedPref.all
    }

    fun saveCache(context: Context, key: String, response: String?) {
        set(
            context,
            key,
            response
        )
    }

    fun checkCache(context: Context, key: String): Boolean {
        return get(
            context,
            key,
            ""
        ).isNotEmpty()
    }
}