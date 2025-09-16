package com.majedul.run.presentation.run_overview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.majedul.core.presentation.designsystem.AnalyticsIcon
import com.majedul.core.presentation.designsystem.LogoIcon
import com.majedul.core.presentation.designsystem.LogoutIcon
import com.majedul.core.presentation.designsystem.MajedTheme
import com.majedul.core.presentation.designsystem.RunIcon
import com.majedul.core.presentation.designsystem.components.MajedFloatingActionButton
import com.majedul.core.presentation.designsystem.components.MajedScaffold
import com.majedul.core.presentation.designsystem.components.MajedToolbar
import com.majedul.core.presentation.designsystem.components.util.DropdownItem
import com.majedul.run.presentation.R
import org.koin.androidx.compose.koinViewModel


@Composable
fun RunoverViewScreenRoot(
    viewModel: RunOverViewViewModel = koinViewModel(),
    onStartClick: () -> Unit,
) {
    RunOverviewScreen(
        onAction = { action ->
            when (action) {
                is RunOverviewAction.OnStartClick -> onStartClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RunOverviewScreen(
    onAction: (RunOverviewAction) -> Unit
) {
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    MajedScaffold(
        topAppBar = {
            MajedToolbar(
                showBackButton = false,
                title = stringResource(id = R.string.majed),
                scrollBehavior = scrollBehavior,
                menuItems = listOf(
                    DropdownItem(
                        icon = AnalyticsIcon,
                        title = stringResource(id = R.string.analytics)
                    ),
                    DropdownItem(
                        icon = LogoutIcon,
                        title = stringResource(id = R.string.logout)
                    ),
                ),
                onMenuItemCLick = { index ->
                    when (index) {
                        0 -> onAction(RunOverviewAction.OnAnalyticsClick)
                        1 -> onAction(RunOverviewAction.OnLogoutCLick)
                    }
                },
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            MajedFloatingActionButton(
                icon = RunIcon,
                onClick = {
                    onAction(RunOverviewAction.OnStartClick)
                }
            )
        }
    ) { padding ->

    }
}

@Preview
@Composable
private fun RunOverviewScreenPreview() {
    MajedTheme {
        RunOverviewScreen(
            onAction = {}
        )
    }
}