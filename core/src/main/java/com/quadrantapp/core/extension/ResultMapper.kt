package com.quadrantapp.core.extension

import com.quadrantapp.core.model.Result
import com.quadrantapp.core.model.ResultDto

inline fun <reified T> ResultDto.toResult(data: T?): Result<T> {
    return Result(data)
}