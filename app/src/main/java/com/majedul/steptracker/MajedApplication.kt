package com.majedul.steptracker

import android.app.Application
import com.majedul.auth.data.di.authDataModule
import com.majedul.auth.presentation.di.authViewModelModule
import com.majedul.core.data.di.coreDataModule
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
                appModule,
                coreDataModule
            )
        }
    }
}