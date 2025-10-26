package com.majedul.run.presentation.active_run.di

import com.majedul.run.presentation.active_run.ActiveRunViewModel
import com.majedul.run.presentation.run_overview.RunOverViewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val runViewModelModule = module {
    viewModelOf(::RunOverViewViewModel)
    viewModelOf(::ActiveRunViewModel)
}