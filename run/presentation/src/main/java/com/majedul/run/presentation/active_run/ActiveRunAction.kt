package com.majedul.run.presentation.active_run

sealed interface ActiveRunAction {
    data object OnToggleRunClick : ActiveRunAction
    data object OnFinishRunCLick : ActiveRunAction
    data object OnResumeRunCLick : ActiveRunAction
    data object OnBackCLick : ActiveRunAction
    data class SubmitLocationPermissionInfo(
        val acceptLocationPermission: Boolean,
        val showLocationPermissionRationale: Boolean
    ) : ActiveRunAction

    data class SubmitNotificationPermissionInfo(
        val acceptNotificationPermission: Boolean,
        val showNotificationPermissionRationale: Boolean
    ): ActiveRunAction
    data object  dismissRationalDialog: ActiveRunAction
}