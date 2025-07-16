package com.majedul.presentation.feature.register

sealed interface RegistrationAction {
    data object OnTogglePasswordVisibilityClick : RegistrationAction
    data object OnLoginClick : RegistrationAction
    data object OnRegisterClick: RegistrationAction

}