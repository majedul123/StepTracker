package com.majedul.run.presentation.active_run.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.majedul.core.presentation.designsystem.MajedTheme
import com.majedul.core.presentation.designsystem.R
import com.majedul.core.presentation.ui.formatted
import com.majedul.core.presentation.ui.toFormattedKM
import com.majedul.core.presentation.ui.toFormattedPace
import com.majedul.run.domain.RunData
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@Composable
fun RunDatCard(
    elapsedTime: Duration,
    runData: RunData,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RunDataItem(
            title = stringResource(R.string.duration),
            value = elapsedTime.formatted(),
            valueFontSize = 32.sp
        )

        Spacer(modifier = Modifier.padding(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            RunDataItem(
                title = stringResource(R.string.distance),
                value = (runData.distanceMeters/1000.0).toFormattedKM(),
                modifier = Modifier.defaultMinSize(75.dp)
            )
            RunDataItem(
                title = stringResource(R.string.pace),
                value = elapsedTime.toFormattedPace(
                    distanceKm = (runData.distanceMeters / 1000.0)
                ),
                modifier = Modifier.defaultMinSize(75.dp)

            )


        }


    }


}

@Composable
private fun RunDataItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
    valueFontSize: TextUnit = 16.sp,
) {

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value, fontSize = valueFontSize, color = MaterialTheme.colorScheme.onSurface
        )

    }


}

@Preview
@Composable
private fun RunDataCardPreview() {

    MajedTheme {
        RunDatCard(
            elapsedTime = 10.minutes, runData = RunData(
                distanceMeters = 3395, pace = 3.minutes
            )
        )
    }

}