package com.majedul.core.domain

import com.majedul.core.domain.run.Run
import kotlin.time.Duration

interface SyncRunSchedular {

    suspend fun scheduleSync(type: SyncType)

    suspend fun cancelAllSyncs()

    sealed interface SyncType {
        data class FetchRuns(val interval: Duration) : SyncType
        data class DeleteRuns(val runId: String) : SyncType
        class CreateRun(val run: Run, val mapPictureBytes: ByteArray) : SyncType
    }
}