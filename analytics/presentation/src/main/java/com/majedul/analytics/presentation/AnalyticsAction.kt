package com.majedul.analytics.presentation

sealed interface AnalyticsAction {

    data object OnBackClick: AnalyticsAction
}