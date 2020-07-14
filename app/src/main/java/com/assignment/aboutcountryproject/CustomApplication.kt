package com.assignment.aboutcountryproject

import android.app.Application
import com.assignment.aboutcountryproject.BuildConfig.DEBUG
import com.assignment.aboutcountryproject.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //startKoin(this, appModule, logger = AndroidLogger(level = DEBUG))

        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@CustomApplication)

            // load properties from assets/koin.properties file
            androidFileProperties()

            // module list
            modules(appModule)
        }
    }
}