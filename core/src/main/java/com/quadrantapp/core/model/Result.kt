package com.quadrantapp.core.model

class Result<T>(
    val data: T? = null,
    val message: String? = null,
    val status: String = "INITIATE",
    val code: String = "XXX"
)