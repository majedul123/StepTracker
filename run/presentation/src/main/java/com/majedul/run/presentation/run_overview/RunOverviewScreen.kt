package com.majedul.run.presentation.run_overview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.majedul.core.presentation.designsystem.LogoIcon
import com.majedul.core.presentation.designsystem.components.MajedScaffold
import com.majedul.core.presentation.designsystem.components.MajedToolbar
import com.majedul.run.presentation.R
import org.koin.androidx.compose.koinViewModel


@Composable
fun RunoverViewScreenRoot(
    viewModel: RunOverViewViewModel = koinViewModel(),
    onLogoutClick: () -> Unit,
    onAnalyticsClick: () -> Unit,
    onStartClick: () -> Unit,
) {

    RunOverViewScreen(viewModel::onAction)

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunOverViewScreen(onAction: (RunOverviewAction) -> Unit) {

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )

    MajedScaffold(
        topAppBar = {
            MajedToolbar(
                showBackButton = true,
                title = stringResource(id = R.string.majed),
                scrollBehavior = scrollBehavior,
                startContent = {
                    Icon(
                        imageVector = LogoIcon,
                        contentDescription = null,
                    )
                },
                modifier = Modifier.size(30.dp)
            )
        }
    ) {


    }

}

@Preview
@Composable
private fun RunOverViewScreenPreview() {

    RunOverViewScreen(onAction = {

    })

}
