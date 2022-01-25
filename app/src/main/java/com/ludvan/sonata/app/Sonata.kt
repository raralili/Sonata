package com.ludvan.sonata.app

import android.app.Application
import com.ludvan.sonata.network.ApiProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Sonata : Application() {

    override fun onCreate() {
        super.onCreate()
        ApiProvider.init("https://kkkkkkk")
    }

}