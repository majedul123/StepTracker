package com.majedul.presentation.feature.register

import com.majedul.core.presentation.ui.UiText

sealed interface RegisterEvent {
    data object RegisterSuccess : RegisterEvent
    data class Error(val error: UiText) : RegisterEvent
}