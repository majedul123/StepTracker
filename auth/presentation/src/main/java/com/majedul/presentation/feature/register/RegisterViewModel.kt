package com.majedul.presentation.feature.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majedul.auth.domain.UserDataValidator
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class RegisterViewModel(private val userDataValidator: UserDataValidator) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    init {
        snapshotFlow { state.email.text.toString() }
            .debounce(400)
            .distinctUntilChanged()
            .onEach {
            val emailValid = userDataValidator.isValidEmail(it)

            state = state.copy(
                isEmailValid = emailValid,
                canRegister = emailValid && state.passwordValidationState.isValidPassword && !state.isRegistering
            )
        }.launchIn(viewModelScope)

        snapshotFlow { state.password.text.toString() }
            .debounce(400)
            .distinctUntilChanged()
            .onEach { password ->
                state = state.copy(
                    passwordValidationState = userDataValidator.validatePassword(password)
                )
            }.launchIn(viewModelScope)
    }

    fun onAction(action: RegistrationAction) {

    }


}