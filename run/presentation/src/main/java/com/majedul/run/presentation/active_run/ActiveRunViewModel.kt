package com.majedul.run.presentation.active_run

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majedul.run.domain.RunTracker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber

class ActiveRunViewModel(
    private val runTracker: RunTracker
) : ViewModel() {

    var state by mutableStateOf(ActiveRunState())
        private set
    private val channel = Channel<ActiveRunEvent>()
    val events = channel.receiveAsFlow()

    private val hasLocationPermission = MutableStateFlow(false)

    init {
        hasLocationPermission.onEach { hasPermission ->
            if (hasPermission) {
                runTracker.startObservingLocation()
            } else {
                runTracker.stopObservingLocation()
            }
        }
            .launchIn(viewModelScope)

        runTracker.currentLocation
            .onEach { location ->
                Timber.d("new location $location")

            }
            .launchIn(viewModelScope)
    }

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
