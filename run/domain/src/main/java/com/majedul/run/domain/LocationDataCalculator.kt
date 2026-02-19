package com.majedul.run.domain

import com.majedul.core.domain.location.LocationWithTimeStamp
import kotlin.math.roundToInt
import kotlin.time.DurationUnit

object LocationDataCalculator {

    fun getTotalDistance(locationList: List<List<LocationWithTimeStamp>>): Int {
        return locationList.sumOf { timeStampPerLine ->
            timeStampPerLine.zipWithNext { location1, location2 ->
                location1.location.location.distanceTo(location2.location.location)
            }.sum().roundToInt()
        }
    }

    fun getMaxSpeedKmh(locations: List<List<LocationWithTimeStamp>>): Double {

        return locations.maxOf { locationSet ->
            locationSet.zipWithNext { location1, location2 ->
                val distance = location1.location.location.distanceTo(location2.location.location)
                val hoursDifference = (location2.durationTimestamp - location1.durationTimestamp).toDouble(DurationUnit.HOURS)
                if (hoursDifference == 0.0) {
                    0.0
                } else {
                    (distance / 1000.0) / hoursDifference
                }
            }.maxOrNull() ?: 0.0
        }
    }

    fun getTotalElevationMeters(locations: List<List<LocationWithTimeStamp>>): Int {
        return locations.sumOf { locationSet ->
            locationSet.zipWithNext { location1, location2 ->
                val altitude1 = location1.location.altitude
                val altitude2 = location2.location.altitude
                (altitude2 - altitude1).coerceAtLeast(0.0)
            }.sum().roundToInt()
        }
    }
}