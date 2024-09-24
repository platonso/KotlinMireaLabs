package com.platon.kotlinmirealabs

import android.app.Application
import com.platon.kotlinmirealabs.DI.retrofitModule
import com.platon.kotlinmirealabs.DI.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(appModule, retrofitModule, roomModule))
        }
    }
}