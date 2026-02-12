package com.majedul.core.domain.run

import com.majedul.core.domain.location.Location
import java.time.ZonedDateTime
import kotlin.time.Duration
import kotlin.time.DurationUnit

data class Run(
    val id: String? = null,
    val duration: Duration,
    val dateTimeUtc: ZonedDateTime,
    val distanceMeters: Int,
    val location: Location,
    val maxSpeedKph: Double,
    val totalElevationMeters: Int,
    val mapPictureUrl: String?
) {

    val avgSpeedKmh: Double
        get() = ((distanceMeters / 1000.0) / duration.toDouble(DurationUnit.HOURS))
}