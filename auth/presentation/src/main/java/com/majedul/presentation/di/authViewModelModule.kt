package com.majedul.presentation.di

import com.majedul.presentation.feature.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val  authViewModelModule = module {
    viewModelOf(::RegisterViewModel)
}