package com.majedul.run.presentation.active_run.maps

import androidx.compose.runtime.Composable
import androidx.compose.ui.util.fastZipWithNext
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polyline
import com.majedul.core.domain.location.LocationWithTimeStamp


@Composable
fun MajedPolyLines(locations: List<List<LocationWithTimeStamp>>) {

    val polyLines = locations.map {
        it.zipWithNext { timeStamp1, timeStamp2 ->
            PolyLineUi(
                location1 = timeStamp1.location.location,
                location2 = timeStamp2.location.location,
                color = PolyLineColorCalculator.locationToColor(timeStamp1, timeStamp2)
            )
        }
    }

    polyLines.forEach { polyLine ->

        polyLine.forEach { polyLineUi ->
            Polyline(
                points = listOf(
                    LatLng(polyLineUi.location1.lat, polyLineUi.location1.long),
                    LatLng(polyLineUi.location2.lat, polyLineUi.location2.long),
                ),
                color = polyLineUi.color,
                jointType = JointType.BEVEL
            )
        }
    }
}