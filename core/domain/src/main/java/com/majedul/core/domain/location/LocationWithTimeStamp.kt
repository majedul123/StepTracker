package com.majedul.core.domain.location

import kotlin.time.Duration

data class LocationWithTimeStamp(
    val location: LocationWithAltitude,
    val durationTimestamp: Duration
)