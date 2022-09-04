package com.example.esteveshopfullytest

import android.app.Application
import com.example.esteveshopfullytest.analytics.StreamFully

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        streamFully= StreamFully(packageName,BuildConfig.VERSION_NAME)
    }

    companion object {
        lateinit var instance: BaseApplication
            private set
    }

    private lateinit var streamFully : StreamFully

    fun getStreamFully() : StreamFully {
        return this.streamFully
    }
}