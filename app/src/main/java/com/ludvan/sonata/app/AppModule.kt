package com.ludvan.sonata.app

import com.ludvan.sonata.app.api.LoginApi
import com.ludvan.sonata.network.ApiProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideLoginApi(): LoginApi {
        return ApiProvider.getInstance().getApi(LoginApi::class.java)
    }
}