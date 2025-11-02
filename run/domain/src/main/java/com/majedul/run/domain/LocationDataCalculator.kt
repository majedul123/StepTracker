package com.majedul.run.domain

import com.majedul.core.domain.location.LocationWithTimeStamp
import kotlin.math.roundToInt

object LocationDataCalculator {

    fun getTotalDistance(locationList: List<List<LocationWithTimeStamp>>): Int {
        return locationList.sumOf { timeStampPerLine ->
            timeStampPerLine.zipWithNext { location1, location2 ->
                location1.location.location.distanceTo(location2.location.location)
            }.sum().roundToInt()
        }
    }
}