package com.majedul.auth.data.di

import com.majedul.auth.data.AuthRepositoryImpl
import com.majedul.auth.data.EmailPatternValidator
import com.majedul.auth.domain.AuthRepository
import com.majedul.auth.domain.PatternValidator
import com.majedul.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}