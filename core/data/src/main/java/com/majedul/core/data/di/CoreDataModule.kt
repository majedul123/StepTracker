package com.majedul.core.data.di

import com.majedul.core.data.auth.EncryptedSessionStorage
import com.majedul.core.data.networking.HttpClientFactory
import com.majedul.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

}