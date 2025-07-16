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
    primary = MajedGreen,
    background = MajedBlack,
    surface = MajedDarkGray,
    secondary = MajedWhite,
    tertiary = MajedWhite,
    primaryContainer = MajedGreen30,
    onPrimary = MajedBlack,
    onBackground = MajedWhite,
    onSurface = MajedWhite,
    onSurfaceVariant = MajedGray,
    error = MajedDarkRed,
    errorContainer = MajedDarkRed5
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