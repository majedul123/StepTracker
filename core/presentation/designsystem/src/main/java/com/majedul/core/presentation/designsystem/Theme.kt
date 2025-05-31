package com.majedul.core.presentation.designsystem

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = majedGreen,
    background = majedBlack,
    surface = majedDarkGray,
    secondary = majedWhite,
    tertiary = majedWhite,
    primaryContainer = majedGreen30,
    onPrimary = majedBlack,
    onBackground = majedWhite,
    onSurface = majedWhite,
    onSurfaceVariant = majedGray,
    error = majedDarkRed,
    errorContainer = majedDarkRed5
)

@Composable
fun MajedTheme(
    content: @Composable () -> Unit
) {
    var colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}