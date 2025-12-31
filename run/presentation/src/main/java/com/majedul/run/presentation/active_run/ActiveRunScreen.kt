package com.majedul.run.presentation.active_run

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.majedul.core.presentation.designsystem.MajedTheme
import com.majedul.core.presentation.designsystem.R
import com.majedul.core.presentation.designsystem.StartIcon
import com.majedul.core.presentation.designsystem.StopIcon
import com.majedul.core.presentation.designsystem.components.MajedActionButton
import com.majedul.core.presentation.designsystem.components.MajedDialog
import com.majedul.core.presentation.designsystem.components.MajedFloatingActionButton
import com.majedul.core.presentation.designsystem.components.MajedOutlinedActionButton
import com.majedul.core.presentation.designsystem.components.MajedScaffold
import com.majedul.core.presentation.designsystem.components.MajedToolbar
import com.majedul.run.presentation.active_run.components.RunDatCard
import com.majedul.run.presentation.active_run.maps.TrackerMap
import com.majedul.run.presentation.active_run.service.ActiveRunService
import com.majedul.run.presentation.util.hasLocationPermission
import com.majedul.run.presentation.util.hasNotificationPermission
import com.majedul.run.presentation.util.shouldShowLocationPermissionRationale
import com.majedul.run.presentation.util.shouldShowNotificationPermissionRationale
import org.koin.androidx.compose.koinViewModel


@Composable
fun ActiveRunScreenRoot(
    onServiceToggle: (isServiceRunning: Boolean) -> Unit,
    viewModel: ActiveRunViewModel = koinViewModel()
) {

    ActiveRunScreen(
        state = viewModel.state,
        onServiceToggle = onServiceToggle,
        onAction = viewModel::onAction
    )


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveRunScreen(
    state: ActiveRunState,
    onServiceToggle: (isServiceRunning: Boolean) -> Unit,
    onAction: (ActiveRunAction) -> Unit
) {

    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
    ) { params ->

        val hasCourseLocationPermission = params[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        val hasFineLocationPermission = params[Manifest.permission.ACCESS_FINE_LOCATION] == true

        val hasNotificationPermission = if (Build.VERSION.SDK_INT >= 35) {
            params[Manifest.permission.POST_NOTIFICATIONS] == true
        } else {
            true
        }

        val activity = context as ComponentActivity
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()

        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission = hasCourseLocationPermission || hasFineLocationPermission,
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptNotificationPermission = hasNotificationPermission,
                showNotificationPermissionRationale = showNotificationRationale
            )
        )
    }

    LaunchedEffect(key1 = true) {

        val activity = context as ComponentActivity
        val showNotificationRationale = activity.shouldShowNotificationPermissionRationale()
        val showLocationRationale = activity.shouldShowLocationPermissionRationale()


        onAction(
            ActiveRunAction.SubmitLocationPermissionInfo(
                acceptedLocationPermission =  context.hasLocationPermission(),
                showLocationRationale = showLocationRationale
            )
        )
        onAction(
            ActiveRunAction.SubmitNotificationPermissionInfo(
                acceptNotificationPermission = context.hasNotificationPermission(),
                showNotificationPermissionRationale = showNotificationRationale
            )
        )


        if(!showLocationRationale && !showNotificationRationale){
            permissionLauncher.requestMajedPermission(context)
        }


    }

    LaunchedEffect(key1 = state.isRunFinished) {
        if(state.isRunFinished) {
            onServiceToggle(false)
        }
    }

    LaunchedEffect(key1 = state.shouldTrack) {
        if(context.hasLocationPermission() && state.shouldTrack && !ActiveRunService.isServiceActive) {
            onServiceToggle(true)
        }
    }

    MajedScaffold(
        withGradient = true,
        topAppBar = {
            MajedToolbar(
                showBackButton = true,
                title = stringResource(R.string.active_run),
                onBackCLick = {
                    onAction(ActiveRunAction.OnBackClick)
                },
            )
        },
        floatingActionButton = {
            MajedFloatingActionButton(
                icon = if (state.shouldTrack) {
                    StopIcon
                } else {
                    StartIcon
                },
                onClick = {
                    onAction(ActiveRunAction.OnToggleRunClick)
                },
                iconSize = 20.dp,
                contentDescription = if (state.shouldTrack) {
                    stringResource(R.string.pause_run)
                } else {
                    stringResource(R.string.start_run)
                }
            )
        },


        ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
           TrackerMap(
               isRunFinished = state.isRunFinished,
               currentLocation = state.currentLocation,
               locations = state.runData.locations,
               onSnapShot ={},
               modifier = Modifier.fillMaxSize()
           )
            RunDatCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(paddingValues)
                    .fillMaxWidth()

            )
        }


      /*  if (!state.shouldTrack && state.hasStartRunning) {

            MajedDialog(
                title = stringResource(R.string.running_is_paused),
                onDismiss = {
                    onAction(ActiveRunAction.OnResumeRunCLick)
                },
                description = stringResource(R.string.resume_or_finish_run),
                primaryButton = {
                    MajedActionButton(
                        text = stringResource(R.string.resume),
                        isLoading = false,
                        onClick = {
                            onAction(ActiveRunAction.OnResumeRunCLick)
                        },
                        modifier = Modifier.weight(1f)

                    )
                },
                secondaryButton = {
                    MajedOutlinedActionButton(
                        text = stringResource(id = R.string.finish),
                        isLoading = state.isSavingRun,
                        onClick = {
                            onAction(ActiveRunAction.OnFinishRunCLick)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            )
        }*/

        if (!state.shouldTrack && state.hasStartedRunning) {
            MajedDialog(
                title = stringResource(id = R.string.running_is_paused),
                onDismiss = {
                    onAction(ActiveRunAction.OnResumeRunClick)
                },
                description = stringResource(id = R.string.resume_or_finish_run),
                primaryButton = {
                    MajedActionButton(
                        text = stringResource(id = R.string.resume),
                        isLoading = false,
                        onClick = {
                            onAction(ActiveRunAction.OnResumeRunClick)
                        },
                        modifier = Modifier.weight(.5f)
                    )
                },
                secondaryButton = {
                   MajedOutlinedActionButton(
                        text = stringResource(id = R.string.finish),
                        isLoading = state.isSavingRun,
                        onClick = {
                            onAction(ActiveRunAction.OnFinishRunClick)
                        },
                        modifier = Modifier.weight(.5f)
                    )
                }
            )
        }

        if (state.showLocationRationale || state.showNotificationRationale) {

            MajedDialog(
                title = stringResource(R.string.permission_required),
                onDismiss = {

                },
                description = when {
                    state.showLocationRationale && state.showNotificationRationale -> stringResource(
                        R.string.location_notification_rationale
                    )

                    state.showLocationRationale -> stringResource(R.string.location_rationale)
                    else -> stringResource(R.string.notification_rationale)
                },
                primaryButton = {
                    MajedOutlinedActionButton(
                        text = stringResource(R.string.okay), isLoading = false, onClick = {
                            onAction(ActiveRunAction.DismissRationaleDialog)
                            permissionLauncher.requestMajedPermission(context)
                        })
                },
            )
        }
    }
}

private fun ActivityResultLauncher<Array<String>>.requestMajedPermission(
    context: Context
) {

    val hasLocationPermission = context.hasLocationPermission()
    val hasNotificationPermission = context.hasNotificationPermission()

    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
    )

    val notificationPermission = if (Build.VERSION.SDK_INT >= 33) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else arrayOf()

    when {
        !hasLocationPermission && !hasNotificationPermission -> {
            launch(locationPermissions + notificationPermission)
        }

        !hasLocationPermission -> launch(locationPermissions)
        !hasNotificationPermission -> launch(notificationPermission)
    }

}


@Preview
@Composable
private fun ActiveRunScreenPreview() {

    MajedTheme {
        ActiveRunScreen(
            state = ActiveRunState(), onAction = {}, onServiceToggle = {})
    }

}

