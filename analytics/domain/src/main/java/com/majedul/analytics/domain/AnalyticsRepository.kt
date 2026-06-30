package com.majedul.analytics.domain

interface AnalyticsRepository {
    suspend fun getAnalyticsValue(): AnalyticsValues
}