package com.majedul.run.presentation.active_run.maps

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import com.majedul.core.domain.location.LocationWithTimeStamp
import kotlin.math.abs

object PolyLineColorCalculator {

    fun locationToColor(location1: LocationWithTimeStamp, location2: LocationWithTimeStamp): Color {

        val distance = location1.location.location.distanceTo(location2.location.location)
        val timeDiff =
            abs((location2.durationTimestamp - location1.durationTimestamp).inWholeSeconds)
        val speed = (distance / timeDiff) * 3.6
        return interpolateColor(
            speedInKmh = speed,
            minSpeed = 5.0,
            maxSpeed = 20.0,
            colorStart = Color.Red,
            colorMid = Color.Yellow,
            colorEnd = Color.Green
        )
    }

    private fun interpolateColor(
        speedInKmh: Double,
        minSpeed: Double,
        maxSpeed: Double,
        colorStart: Color,
        colorMid: Color,
        colorEnd: Color

    ): Color {

        val ratio = ((speedInKmh - minSpeed) / (maxSpeed - minSpeed)).coerceIn(0.0..1.0)
        val colorInt = if (ratio <= 0.5) {
            val midRatio = ratio / 0.5
            ColorUtils.blendARGB(colorStart.toArgb(), colorMid.toArgb(), midRatio.toFloat())
        } else {
            val midToEndRatio = (ratio - 0.5) / 0.5
            ColorUtils.blendARGB(colorMid.toArgb(), colorEnd.toArgb(), midToEndRatio.toFloat())
        }

        return Color(colorInt)
    }
}