package com.majedul.run.presentation.active_run

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.majedul.core.presentation.designsystem.MajedTheme
import com.majedul.core.presentation.designsystem.R
import com.majedul.core.presentation.designsystem.StartIcon
import com.majedul.core.presentation.designsystem.StopIcon
import com.majedul.core.presentation.designsystem.components.MajedFloatingActionButton
import com.majedul.core.presentation.designsystem.components.MajedScaffold
import com.majedul.core.presentation.designsystem.components.MajedToolbar
import com.majedul.run.presentation.active_run.components.RunDatCard
import org.koin.androidx.compose.koinViewModel


@Composable
fun ActiveRunScreenRoot(
    viewModel: ActiveRunViewModel = koinViewModel()
) {

    ActiveRunScreen(
        state = viewModel.state, onAction = viewModel::action
    )


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActiveRunScreen(
    state: ActiveRunState, onAction: (ActiveRunAction) -> Unit
) {

    MajedScaffold(
        withGradient = true,
        topAppBar = {
            MajedToolbar(
                showBackButton = true,
                title = stringResource(R.string.active_run),
                onBackCLick = {
                    onAction(ActiveRunAction.OnBackCLick)
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

            RunDatCard(
                elapsedTime = state.elapsedTime,
                runData = state.runData,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(paddingValues)
                    .fillMaxWidth()

            )
        }


    }


}

@Preview
@Composable
private fun activeRunScreenPreview() {

    MajedTheme {
        ActiveRunScreen(
            state = ActiveRunState(), onAction = {})
    }

}

