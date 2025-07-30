package com.majedul.presentation.feature.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import asUiText
import com.majedul.auth.domain.AuthRepository
import com.majedul.auth.domain.UserDataValidator
import com.majedul.core.domain.util.DataError
import com.majedul.core.domain.util.Result
import com.majedul.core.presentation.ui.UiText
import com.majedul.presentation.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


class RegisterViewModel(
    private val userDataValidator: UserDataValidator, private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()


    init {
        snapshotFlow { state.email.text.toString() }.debounce(400).distinctUntilChanged().onEach {
            val emailValid = userDataValidator.isValidEmail(it)

            state = state.copy(
                isEmailValid = emailValid,
                canRegister = emailValid && state.passwordValidationState.isValidPassword && !state.isRegistering
            )
        }.launchIn(viewModelScope)

        snapshotFlow { state.password.text.toString() }.debounce(400).distinctUntilChanged()
            .onEach { password ->
                val passwordValidationState = userDataValidator.validatePassword(password)
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isEmailValid && passwordValidationState.isValidPassword && !state.isRegistering
                )
            }.launchIn(viewModelScope)
    }

    fun onAction(action: RegistrationAction) {

        when (action) {
            RegistrationAction.OnRegisterClick -> register()
            RegistrationAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordVisible = !state.isPasswordVisible
                )
            }

            else -> Unit
        }

    }


    private fun register() {
        viewModelScope.launch {
            state = state.copy(isRegistering = true)
            val result = authRepository.register(
                state.email.text.toString().trim(), state.password.text.toString().trim()
            )
            state = state.copy(isRegistering = false)
            when (result) {
                is Result.Error -> {
                    if (result.error == DataError.Network.CONFLICT) {
                        eventChannel.send(RegisterEvent.Error(UiText.StringResource(R.string.error_user_already_exist)))
                    } else {
                        eventChannel.send(RegisterEvent.Error(result.error.asUiText()))
                    }
                }

                is Result.Success-> {
                    eventChannel.send(RegisterEvent.RegisterSuccess)
                }
            }

        }
    }
}