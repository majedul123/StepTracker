package com.majedul.presentation.feature.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.majedul.auth.domain.PasswordValidationState
import com.majedul.auth.domain.UserDataValidator
import com.majedul.core.presentation.designsystem.CheckIcon
import com.majedul.core.presentation.designsystem.CrossIcon
import com.majedul.core.presentation.designsystem.EmailIcon
import com.majedul.core.presentation.designsystem.MajedDarkRed
import com.majedul.core.presentation.designsystem.MajedGray
import com.majedul.core.presentation.designsystem.MajedGreen
import com.majedul.core.presentation.designsystem.MajedTheme
import com.majedul.core.presentation.designsystem.Poppins
import com.majedul.core.presentation.designsystem.components.GradiantBackground
import com.majedul.core.presentation.designsystem.components.MajedActionButton
import com.majedul.core.presentation.designsystem.components.MajedTPasswordTextField
import com.majedul.core.presentation.designsystem.components.MajedTextField
import com.majedul.presentation.R
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegisterScreenRoot(
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    RegisterScreen(
        state = viewModel.state, onAction = viewModel::onAction
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState, onAction: (RegistrationAction) -> Unit
) {
    GradiantBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {

            Text(
                text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.headlineMedium,
            )
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins, color = MajedGray
                    )
                ) {
                    append(stringResource(R.string.already_have_an_account) + " ")
                    pushStringAnnotation(
                        tag = "clickable_text", stringResource(
                            R.string.login
                        )
                    )
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = Poppins
                        )
                    ) {
                        append(stringResource(R.string.login))
                    }
                }

            }
            ClickableText(
                text = annotatedString, onClick = { offset ->

                    annotatedString.getStringAnnotations(
                        tag = "clickable_text", start = offset, end = offset
                    ).firstOrNull()?.let {
                        onAction(RegistrationAction.OnLoginClick)
                    }

                })

            Spacer(modifier = Modifier.height(48.dp))

            MajedTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if (state.isEmailValid) {
                    CheckIcon
                } else {
                    null
                },
                hint = stringResource(R.string.example_email),
                title = stringResource(R.string.email),
                additionalInfo = stringResource(R.string.must_be_a_valid_email),
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Email
            )

            Spacer(Modifier.height(16.dp))

            MajedTPasswordTextField(
                state = state.password,
                hint = stringResource(R.string.password),
                title = stringResource(R.string.password),
                modifier = Modifier.fillMaxWidth(),
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(RegistrationAction.OnTogglePasswordVisibilityClick)
                })

            Spacer(modifier = Modifier.height(16.dp))

            PasswordRequirement(
                text = stringResource(
                    R.string.at_least_9_characters, UserDataValidator.MIN_PASSWORD_LENGTH
                ), isValid = state.passwordValidationState.hasMinLength
            )
            Spacer(modifier = Modifier.height(4.dp))

            PasswordRequirement(
                text = stringResource(
                    R.string.at_least_one_number
                ), isValid = state.passwordValidationState.hasNumber
            )
            Spacer(modifier = Modifier.height(4.dp))

            PasswordRequirement(
                text = stringResource(
                    R.string.contains_lower_case_character
                ), isValid = state.passwordValidationState.hasLowerCaseCharacter
            )
            Spacer(modifier = Modifier.height(4.dp))

            PasswordRequirement(
                text = stringResource(
                    R.string.contains_upper_case_character
                ), isValid = state.passwordValidationState.hasUpperCaseCharacter
            )
            Spacer(modifier = Modifier.height(32.dp))


            MajedActionButton(
                text = stringResource(R.string.register),
                isLoading = state.isRegistering,
                modifier = Modifier.fillMaxWidth(),
                enabled = state.canRegister,
                onClick = {
                    onAction(RegistrationAction.OnRegisterClick)
                }
            )

        }
    }

}


@Composable
fun PasswordRequirement(
    text: String, isValid: Boolean, modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = if (isValid) {
                CheckIcon
            } else {
                CrossIcon
            },
            contentDescription = null,
            tint = if (isValid) {
                MajedGreen
            } else {
                MajedDarkRed
            },
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp
        )

    }

}

@Preview
@Composable
private fun RegisterScreenPreview() {
    MajedTheme {
        RegisterScreen(
            state = RegisterState(
                passwordValidationState = PasswordValidationState(
                    hasNumber = true
                )
            ), onAction = {})
    }


}