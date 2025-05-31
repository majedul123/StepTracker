package com.majedul.core.presentation.designsystem.feature


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.majedul.core.presentation.designsystem.LogoIcon
import com.majedul.core.presentation.designsystem.MajedTheme
import com.majedul.core.presentation.designsystem.R
import com.majedul.core.presentation.designsystem.components.GradiantBackground
import com.majedul.core.presentation.designsystem.components.MajedActionButton
import com.majedul.core.presentation.designsystem.components.MajedOutlinedActionButton


@Composable
fun IntroScreenRoot(
    onSignUpClick: () -> Unit, onSignInClick: () -> Unit
) {

    IntroScreen(onAction = { action ->
        when (action) {
            is IntroAction.onSignInClick -> onSignInClick()
            is IntroAction.onSignUpnClick -> onSignUpClick()
        }
    })
}

@Composable
fun IntroScreen(
    onAction: (IntroAction) -> Unit
) {
    GradiantBackground(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), contentAlignment = Alignment.Center
        ) {
            MajedLogoVertical()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 48.dp)
        ) {
            Text(
                text = "Welcome to StepTracker",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Improve you fitness with StepTracker",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(32.dp))

            MajedOutlinedActionButton(text = stringResource(R.string.sign_in),
                isLoading = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(IntroAction.onSignInClick)
                })


            Spacer(modifier = Modifier.height(16.dp))
            MajedActionButton(text = stringResource(R.string.sign_up),
                isLoading = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(IntroAction.onSignUpnClick)
                }

            )

        }
    }
}

@Composable
private fun MajedLogoVertical(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = LogoIcon,
            contentDescription = "logo",
            tint = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Steptracker",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground

        )
    }

}

@Preview
@Composable
private fun interScreenPreview() {

    MajedTheme {
        IntroScreen(onAction = {})

    }

}