package com.majedul.analytics.data.di

import com.majedul.analytics.data.RoomAnalyticsRepository
import com.majedul.analytics.domain.AnalyticsRepository
import com.majedul.core.database.RunDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsModule = module{
    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()
    single{
        get<RunDatabase>().analyticsDao
    }
}