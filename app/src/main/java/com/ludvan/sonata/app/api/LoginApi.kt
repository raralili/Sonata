package com.ludvan.sonata.app.api

import com.ludvan.sonata.network.ApiResult
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {

    @FormUrlEncoded
    @POST("login")
    fun login(@FieldMap map: Map<String, String>): Observable<ApiResult<LoginBean?>>
}