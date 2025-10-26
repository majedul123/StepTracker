@file:OptIn(ExperimentalFoundationApi::class)

package com.majedul.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState

data class LoginState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val isLogging: Boolean = false,
    val canLogin: Boolean = false,

//    val isEmailValid: Boolean = false,
//    val passwordValidationState: PasswordValidationState = PasswordValidationState(),

)
