package com.majedul.core.database.mappers

import com.majedul.core.database.entity.RunEntity
import com.majedul.core.domain.location.Location
import com.majedul.core.domain.run.Run
import org.bson.types.ObjectId
import java.time.Instant
import java.time.ZoneId
import kotlin.time.Duration.Companion.milliseconds

fun RunEntity.toRun(): Run {

    return Run(
        id = id,
        duration = durationMillis.milliseconds,
        dateTimeUtc = Instant.parse(dateTimeUtc).atZone(ZoneId.of("UTC")),
        distanceMeters = distanceMeters,
        location = Location(latitude, longitude),
        maxSpeedKph = maxSpeedKmh,
        totalElevationMeters = totalElevationMeters,
        mapPictureUrl = mapPictureUrl
    )
}

fun Run.toRunEntity(): RunEntity{

    return RunEntity(
        durationMillis = duration.inWholeMilliseconds,
        distanceMeters = distanceMeters,
        dateTimeUtc = dateTimeUtc.toInstant().toString(),
        latitude = location.lat,
        longitude = location.long,
        avgSpeedKmh = avgSpeedKmh,
        maxSpeedKmh = maxSpeedKph,
        totalElevationMeters = totalElevationMeters,
        mapPictureUrl = mapPictureUrl,
        id = id?: ObjectId().toHexString()
    )
}