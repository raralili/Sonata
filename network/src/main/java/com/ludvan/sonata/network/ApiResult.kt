package com.ludvan.sonata.network

data class ApiResult<T>(
    val code: String,
    val data: T?,
    val msg: String,
    val success: Boolean
)