package com.majedul.steptracker

import android.app.Application
import com.majedul.di.authDataModule
import com.majedul.presentation.di.authViewModelModule
import com.majedul.steptracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MajedApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@MajedApplication)
            modules(
                authDataModule,
                authViewModelModule,
                appModule
            )
        }
    }
}