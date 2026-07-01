package com.majedul.analytics.presentation

import com.majedul.analytics.domain.AnalyticsValues
import com.majedul.core.presentation.ui.formatted
import com.majedul.core.presentation.ui.toFormattedKM
import com.majedul.core.presentation.ui.toFormattedKMh
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

fun Duration.toFormattedDuration(): String {
    val days = toLong(DurationUnit.DAYS)
    val hours = toLong(DurationUnit.HOURS) % 24
    val minutes = toLong(DurationUnit.MINUTES) % 60
    return "${days}d ${hours}h ${minutes}m"
}

fun AnalyticsValues.toAnalyticsDashboardState(): AnalyticsDashboardState {
    return AnalyticsDashboardState(
        totalDistanceRun = (totalDistanceRun / 1000.0).toFormattedKM(),
        totalTimeRun = totalTimeRun.toFormattedDuration(),
        fastestEverRun = fastestEverRun.toFormattedKMh(),
        avgDistance = (avgDistancePerRun / 1000.0).toFormattedKM(),
        avgPace = avgPacePerRun.seconds.formatted()
    )
}