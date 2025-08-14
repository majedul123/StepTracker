package com.majedul.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.majedul.auth.presentation.R
import com.majedul.core.presentation.designsystem.EmailIcon
import com.majedul.core.presentation.designsystem.MajedTheme
import com.majedul.core.presentation.designsystem.Poppins
import com.majedul.core.presentation.designsystem.components.GradientBackground
import com.majedul.core.presentation.designsystem.components.MajedActionButton
import com.majedul.core.presentation.designsystem.components.MajedPasswordTextField
import com.majedul.core.presentation.designsystem.components.MajedTextField
import com.majedul.core.presentation.ui.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)

@Composable
fun LoginScreenRoot(
    onLoginSuccess: () -> Unit,
    onSignUpCLik: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val keyBoardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is LoginEvent.Error -> {
                keyBoardController?.hide()
                Toast.makeText(context, event.error.asString(context), Toast.LENGTH_SHORT).show()
            }

            LoginEvent.LoginSuccess -> {
                keyBoardController?.hide()
                Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show()

                onLoginSuccess()
            }
        }


    }

    LoginScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                LoginAction.OnRegisterClick -> {
                    onSignUpCLik()
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }

    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    state: LoginState, onAction: (LoginAction) -> Unit
) {

    GradientBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {

            Text(
                text = stringResource(R.string.hi_there),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineMedium

            )
            Text(
                text = stringResource(R.string.running_companion_text),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(Modifier.height(48.dp))

            MajedTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = null,
                hint = stringResource(R.string.example_email),
                title = stringResource(R.string.email),
                modifier = Modifier.fillMaxWidth(),
                error = null,
            )

            Spacer(Modifier.height(16.dp))

            MajedPasswordTextField(
                state = state.password,
                hint = stringResource(R.string.password),
                title = stringResource(R.string.password),
                modifier = Modifier.fillMaxWidth(),
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(LoginAction.OnTogglePasswordVisibilityClick)
                },
            )

            Spacer(modifier = Modifier.height(32.dp))

            MajedActionButton(
                text = stringResource(R.string.login),
                isLoading = state.isLogging,
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.canLogin,
                onClick = {
                    onAction.invoke(LoginAction.OnLoginClick)
                })


            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins, color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ) {
                    append(stringResource(id = R.string.do_not_have_an_account) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text", annotation = stringResource(id = R.string.login)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = Poppins
                        )
                    ) {
                        append(stringResource(id = R.string.sign_up))
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                ClickableText(
                    text = annotatedString, onClick = { offset ->
                        annotatedString.getStringAnnotations(
                            tag = "clickable_text", start = offset, end = offset
                        ).firstOrNull()?.let {
                            onAction(LoginAction.OnRegisterClick)
                        }
                    })
            }
        }

    }

}


@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun LoginScreenPreview() {
    MajedTheme {
        LoginScreen(
            state = LoginState(), onAction = {})
    }
}