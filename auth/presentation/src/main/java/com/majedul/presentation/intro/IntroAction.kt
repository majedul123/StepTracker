package com.majedul.presentation.intro

sealed interface IntroAction {

    data object  onSignInClick: IntroAction
    data object  onSignUpnClick: IntroAction
}