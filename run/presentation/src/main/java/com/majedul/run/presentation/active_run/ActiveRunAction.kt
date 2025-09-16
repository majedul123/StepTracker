package com.majedul.run.presentation.active_run

sealed interface ActiveRunAction {
    data object OnToggleRunClick : ActiveRunAction
    data object OnFinishRunCLick : ActiveRunAction
    data object OnResumeRunCLick : ActiveRunAction
    data object OnBackCLick : ActiveRunAction
}