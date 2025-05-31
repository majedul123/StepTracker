package com.majedul.core.presentation.designsystem.feature

sealed interface IntroAction {

    data object  onSignInClick: IntroAction
    data object  onSignUpnClick: IntroAction
}