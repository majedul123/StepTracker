package com.majedul.presentation.feature.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    fun onAction(action: RegistrationAction) {

    }


}