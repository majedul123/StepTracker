package com.majedul.run.location

import android.location.Location
import com.majedul.core.domain.location.LocationWithAltitude


fun Location.toLocationWithAttitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = com.majedul.core.domain.location.Location(
            lat = latitude,
            long = longitude
        ),
        altitude = altitude
    )
}