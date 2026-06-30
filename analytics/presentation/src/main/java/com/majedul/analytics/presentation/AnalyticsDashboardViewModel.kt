package com.majedul.analytics.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.majedul.analytics.domain.AnalyticsRepository

class AnalyticsDashboardViewModel(
    private val  analyticsRepository: AnalyticsRepository
) : ViewModel() {

    var state by mutableStateOf<AnalyticsDashboardState?>(null)
        private set

    fun onAction(action: AnalyticsAction){

    }
}