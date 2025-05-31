package com.majedul.core.presentation.designsystem.gerbaje

sealed interface IntroAction {

    data object  onSignInClick: IntroAction
    data object  onSignUpnClick: IntroAction
}