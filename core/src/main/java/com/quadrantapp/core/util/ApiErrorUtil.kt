package com.quadrantapp.core.util

import timber.log.Timber
//import com.google.gson.Gson
//import com.myxlultimate.app.domain.model.ApiError
//import retrofit2.HttpException
import java.lang.Exception

object ApiErrorUtil {

//    fun parseAsJson(e: HttpException): String {
//        return Gson()
//            .toJson(parseError(e))
//    }

//    fun parseError(e: HttpException): ApiError {
//        val apiError = try {
//            Gson()
//                .fromJson(
//                    e.response()?.errorBody()!!.charStream(),
//                    ApiError::class.java
//                )
//        } catch (gsonExc: Exception) {
//            ApiError(
//                code = 0,
//                message = e.message!!
//            )
//        }
//
//        apiError.httpStatusCode = e.code()
//
//        return apiError
//    }

//    fun parseFromJson(json: String): ApiError? {
//        return try {
//            Gson()
//                .fromJson(
//                    json,
//                    ApiError::class.java
//                )
//        } catch (e: Exception) {
//            null
//        }
//    }

}