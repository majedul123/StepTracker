package com.majedul.run.presentation.active_run

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majedul.run.domain.RunTracker
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class ActiveRunViewModel(
    private val runTracker: RunTracker
) : ViewModel() {

    var state by mutableStateOf(ActiveRunState())
        private set
    private val channel = Channel<ActiveRunEvent>()
    val events = channel.receiveAsFlow()

    private val hasLocationPermission = MutableStateFlow(false)

    private val shouldTrack = snapshotFlow {
        state.shouldTrack
    }.stateIn(viewModelScope, SharingStarted.Lazily, state.shouldTrack)

    private val isTracking = combine(shouldTrack, hasLocationPermission) { shouldTrack, hasPermission ->
            shouldTrack && hasPermission
        }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
        hasLocationPermission.onEach { hasPermission ->
            if (hasPermission) {
                runTracker.startObservingLocation()
            } else {
                runTracker.stopObservingLocation()
            }
        }
            .launchIn(viewModelScope)

        isTracking
            .onEach { isTracking ->
                runTracker.setIsTracking(isTracking)
            }.launchIn(viewModelScope)

        runTracker
            .currentLocation
            .onEach {
                state = state.copy(currentLocation = it?.location)
            }
            .launchIn(viewModelScope)

        runTracker
            .runData
            .onEach {
                state = state.copy(runData = it)
            }
            .launchIn(viewModelScope)

        runTracker
            .elapsedTime
            .onEach {
                state = state.copy(elapsedTime = it)
            }
            .launchIn(viewModelScope)


    }

    fun action(action: ActiveRunAction) {

        when (action) {
            is ActiveRunAction.OnBackCLick -> {
                state = state.copy(
                    shouldTrack = false
                )
            }

            ActiveRunAction.OnFinishRunCLick -> {

            }

            ActiveRunAction.OnResumeRunCLick -> {
                state = state.copy(
                    shouldTrack = true
                )
            }

            ActiveRunAction.OnToggleRunClick -> {

                state = state.copy(
                    hasStartRunning = true,
                    shouldTrack = !state.shouldTrack
                )
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
