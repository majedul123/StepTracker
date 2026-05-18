package com.majedul.run.data.di

import com.majedul.core.domain.SyncRunSchedular
import com.majedul.run.data.CreateRunWorker
import com.majedul.run.data.DeleteRunWorker
import com.majedul.run.data.FetchRunWorker
import com.majedul.run.data.SyncRunWorkerSchedular
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module{
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunWorker)
    workerOf(::DeleteRunWorker)

    singleOf(::SyncRunWorkerSchedular).bind<SyncRunSchedular>()
}