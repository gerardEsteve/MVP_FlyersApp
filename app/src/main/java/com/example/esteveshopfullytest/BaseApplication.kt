package com.example.esteveshopfullytest

import android.app.Application
import com.example.esteveshopfullytest.analytics.StreamFully
import com.example.esteveshopfullytest.model.FlyerRepository
import com.example.esteveshopfullytest.model.database.FlyerRoomDatabase
import com.example.esteveshopfullytest.presenters.FlyerPresenterInterface
import com.example.esteveshopfullytest.presenters.FlyersListPresenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BaseApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    // lazy -> initializes the first time it's used, not when the app is created
    val database by lazy { FlyerRoomDatabase.getDatabase(this,applicationScope) }

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