package com.majedul.run.data.di

import com.majedul.run.data.CreateRunWorker
import com.majedul.run.data.DeleteRunWorker
import com.majedul.run.data.FetchRunWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val runDataModule = module{
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunWorker)
    workerOf(::DeleteRunWorker)
}