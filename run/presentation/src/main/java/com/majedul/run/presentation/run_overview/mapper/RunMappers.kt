package com.majedul.run.presentation.run_overview.mapper

import com.majedul.core.domain.run.Run
import com.majedul.core.presentation.ui.formatted
import com.majedul.core.presentation.ui.toFormattedKM
import com.majedul.core.presentation.ui.toFormattedKMh
import com.majedul.core.presentation.ui.toFormattedMeters
import com.majedul.core.presentation.ui.toFormattedPace
import com.majedul.run.presentation.run_overview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Run.toRunUI(): RunUi {
    val dateTimeInLocalTime = dateTimeUtc.withZoneSameInstant(
        ZoneId.systemDefault()
    )
    val formattedDateTime =
        DateTimeFormatter.ofPattern("MMM dd, yyyy - hh:mma").format(dateTimeInLocalTime)
    val distanceKm = distanceMeters / 1000.0

    return RunUi(
        id = id!!,
        duration = duration.formatted(),
        datTime = formattedDateTime,
        distance = distanceKm.toFormattedKM(),
        avgSpeed = avgSpeedKmh.toFormattedKMh(),
        maxSpeed = maxSpeedKmh.toFormattedKMh(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl
    )

}