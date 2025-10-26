package com.majedul.run.domain

import com.majedul.core.domain.location.Location
import com.majedul.core.domain.location.LocationWithTimeStamp
import kotlin.time.Duration

data class RunData(
    val distanceMeters: Int = 0,
    val pace: Duration = Duration.ZERO,
    val locations: List<List<LocationWithTimeStamp>> = emptyList(),
)