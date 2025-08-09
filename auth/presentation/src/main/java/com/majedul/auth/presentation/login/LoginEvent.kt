package com.majedul.auth.presentation.login

import com.majedul.core.presentation.ui.UiText

sealed interface LoginEvent {
    data object LoginSuccess: LoginEvent
    data class Error(val error: UiText): LoginEvent
}