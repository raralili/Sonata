package com.ludvan.sonata.network


class ApiException(val result: ApiResult<*>) : Throwable(result.msg)