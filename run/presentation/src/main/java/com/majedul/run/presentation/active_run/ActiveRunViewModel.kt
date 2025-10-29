package com.majedul.run.presentation.active_run

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class ActiveRunViewModel : ViewModel() {

    var state by mutableStateOf(ActiveRunState())
        private set
    private val channel = Channel<ActiveRunEvent>()
    val events = channel.receiveAsFlow()

    private val hasLocationPermission = MutableStateFlow(false)

    fun action(action: ActiveRunAction) {

        when (action) {
            is ActiveRunAction.OnBackCLick -> {

            }

            ActiveRunAction.OnFinishRunCLick -> {

            }

            ActiveRunAction.OnResumeRunCLick -> {

            }

            ActiveRunAction.OnToggleRunClick -> {

            }

            is ActiveRunAction.SubmitLocationPermissionInfo -> {
                hasLocationPermission.value = action.acceptLocationPermission
                state = state.copy(
                    showLocationRationale = action.showLocationPermissionRationale,
                )
            }

            is ActiveRunAction.SubmitNotificationPermissionInfo -> {
                state = state.copy(
                    showNotificationRationale = action.showNotificationPermissionRationale
                )
            }

            ActiveRunAction.dismissRationalDialog -> {
                state = state.copy(
                    showLocationRationale = false,
                    showNotificationRationale = false
                )
            }
        }


    }


}
