package com.majedul.core.data.di

import com.majedul.core.data.networking.HttpClientFactory
import org.koin.dsl.module


val coreDataModule = module() {
    single {
        HttpClientFactory().build()
    }
}