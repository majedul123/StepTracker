package com.majedul.run.network.di

import com.majedul.core.domain.run.RemoteRunDataSource
import com.majedul.run.network.KtorRemoteRunDatasource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val networkModule = module{
    singleOf(::KtorRemoteRunDatasource).bind<RemoteRunDataSource>()
}