package com.majedul.di

import com.majedul.auth.domain.PatternValidator
import com.majedul.auth.domain.UserDataValidator
import com.majedul.data.EmailPatternValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
}