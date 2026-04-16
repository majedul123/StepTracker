package com.majedul.run.presentation.run_overview

import com.majedul.run.presentation.run_overview.model.RunUi

sealed interface RunOverviewAction {
    data object OnStartClick: RunOverviewAction
    data object OnLogoutCLick: RunOverviewAction
    data object OnAnalyticsClick: RunOverviewAction
    data class DeleteRun(val runUi: RunUi): RunOverviewAction
}