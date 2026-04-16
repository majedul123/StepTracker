package com.majedul.run.presentation.run_overview.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.majedul.core.domain.location.Location
import com.majedul.core.domain.run.Run
import com.majedul.core.presentation.designsystem.CalendarIcon
import com.majedul.core.presentation.designsystem.MajedTheme
import com.majedul.core.presentation.designsystem.R
import com.majedul.core.presentation.designsystem.RunOutlinedIcon
import com.majedul.run.presentation.run_overview.mapper.toRunUI
import com.majedul.run.presentation.run_overview.model.RunDataUi
import com.majedul.run.presentation.run_overview.model.RunUi
import java.time.ZonedDateTime
import kotlin.math.max
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun  RunListItem(
    runUi: RunUi, onDeleteClick: () -> Unit, modifier: Modifier = Modifier
) {

    var showDropdown by remember {
        mutableStateOf(false)
    }


    Box {
        Column(
            modifier = modifier
                .clip(
                    RoundedCornerShape(15.dp)
                )
                .combinedClickable(onClick = {}, onLongClick = {
                    showDropdown = true
                })
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            MapImage(
                imageUrl = runUi.mapPictureUrl, modifier
            )

            RunningTimeSection(
                duration = runUi.duration, modifier = modifier
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .4f),
            )

            RunningDateSection(runUi.datTime)

            DataGrid(run = runUi, modifier = Modifier.fillMaxWidth())

            DropdownMenu(
                expanded = showDropdown,
                onDismissRequest = {
                    showDropdown = false
                }) {
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(R.string.delete))
                    },
                    onClick = {
                        showDropdown = false
                        onDeleteClick()
                    },
                )
            }
        }
    }

}

@Composable
private fun RunningDateSection(datTime: String, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = CalendarIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.width(16.dp))

        Text(
            text = datTime, color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DataGrid(
    run: RunUi, modifier: Modifier = Modifier
) {

    val runDataUiList = listOf(
        RunDataUi(
            name = stringResource(R.string.distance), value = run.distance
        ), RunDataUi(
            name = stringResource(R.string.pace), value = run.pace
        ), RunDataUi(
            name = stringResource(R.string.avg_speed), value = run.avgSpeed
        ), RunDataUi(
            name = stringResource(R.string.max_speed), value = run.maxSpeed
        ), RunDataUi(
            name = stringResource(R.string.total_elevation), value = run.totalElevation
        )
    )

    var maxWidth by remember {
        mutableIntStateOf(0)
    }

    val maxWidthDp = with(LocalDensity.current) { maxWidth.toDp() }

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        runDataUiList.forEach { runDataUi ->
            GridCell(
                runDataUi = runDataUi,
                modifier = Modifier
                    .defaultMinSize(minWidth = maxWidthDp)
                    .onSizeChanged {
                        maxWidth = max(it.width, maxWidth)
                    })

        }


    }


}

@Composable
fun RunningTimeSection(duration: String, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = .1f))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(4.dp), contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = RunOutlinedIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.total_running_time),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp
            )

            Text(
                text = duration, color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp
            )

        }

    }

}

@Composable
private fun MapImage(
    imageUrl: String?, modifier: Modifier
) {
    SubcomposeAsyncImage(
        model = imageUrl,
        contentDescription = stringResource(R.string.run_map),
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
            .clip(RoundedCornerShape(15.dp)),
        loading = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        error = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.errorContainer),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.error_could_not_load_image),
                    color = MaterialTheme.colorScheme.error,

                    )
            }
        }

    )


}

@Composable
private fun GridCell(
    runDataUi: RunDataUi, modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = runDataUi.name,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 12.sp
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = runDataUi.value, color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp
        )


    }
}

@Preview
@Composable
private fun RunListItemPreview() {
    MajedTheme {
        RunListItem(
            runUi = Run(
                id = "123",
                duration = 10.minutes + 30.seconds,
                dateTimeUtc = ZonedDateTime.now(),
                distanceMeters = 234,
                location = Location(23.0, 89.0),
                maxSpeedKmh = 23.3894474,
                totalElevationMeters = 123,
                mapPictureUrl = null,
            ).toRunUI(),
            onDeleteClick = {},

            )
        //   RunningTimeSection("00:00:23", Modifier.fillMaxWidth())
    }

}