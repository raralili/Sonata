package com.ludvan.sonata.network

import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider private constructor() {

    companion object {

        @Volatile
        private var instance: ApiProvider? = null

        private var BASE_URL = ""

        fun getInstance(): ApiProvider {
            if (instance == null) throw NullPointerException("instance of HttpClientProvider is not init")
            return instance as ApiProvider
        }

        fun init(baseUrl: String) {
            BASE_URL = baseUrl
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = ApiProvider()
                    }
                }
            }
        }
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()

    fun <T> getApi(api: Class<T>) = retrofit.create(api)

}