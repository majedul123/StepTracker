package com.majedul.presentation.feature.intro

sealed interface IntroAction {

    data object  onSignInClick: IntroAction
    data object  onSignUpnClick: IntroAction
}