package com.majedul.run.data

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.await
import com.majedul.core.database.dao.RunPendingSyncDao
import com.majedul.core.database.entity.DeleteRunSyncEntity
import com.majedul.core.database.entity.RunPendingSyncEntity
import com.majedul.core.database.mappers.toRunEntity
import com.majedul.core.domain.SessionStorage
import com.majedul.core.domain.run.Run
import com.majedul.core.domain.run.RunId
import com.majedul.core.domain.SyncRunSchedular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toJavaDuration

class SyncRunWorkerSchedular(
    private val context: Context,
    private val pendingSyncDao: RunPendingSyncDao,
    private val sessionStorage: SessionStorage,
    private val applicationScope: CoroutineScope
) : SyncRunSchedular {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleSync(type: SyncRunSchedular.SyncType) {
        when (type) {
            is SyncRunSchedular.SyncType.FetchRuns -> scheduleFetchRunsWorker(type.interval)
            is SyncRunSchedular.SyncType.CreateRun -> scheduleCreateRunWorker(type.run, type.mapPictureBytes)
            is SyncRunSchedular.SyncType.DeleteRuns -> scheduleDeleteRunWorker(type.runId)
        }

    }

    private suspend fun scheduleCreateRunWorker(run: Run, mapPicture: ByteArray) {
        val userId = sessionStorage.get()?.userId ?: return
        val pendingRun = RunPendingSyncEntity(
            run = run.toRunEntity(), mapPictureBytes = mapPicture, userId = userId
        )
        pendingSyncDao.upsertRunPendingSyncEntity(pendingRun)

        val workRequest =
            OneTimeWorkRequestBuilder<CreateRunWorker>()
                .addTag("create_work")
                .setConstraints(
                    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                )
                .setBackoffCriteria(
                    backoffPolicy = BackoffPolicy.EXPONENTIAL,
                    backoffDelay = 2000L,
                    timeUnit = TimeUnit.MILLISECONDS
                )
                .setInputData(
                    inputData = Data.Builder()
                        .putString(CreateRunWorker.RUN_ID, pendingRun.runId)
                        .build()
                )
                .build()

        applicationScope.launch {
            workManager.enqueue(workRequest).await()
        }.join()
    }

    private suspend fun scheduleDeleteRunWorker(runId: RunId) {
        val userId = sessionStorage.get()?.userId ?: return
        val entity = DeleteRunSyncEntity(
            runId = runId,
            userId = userId
        )

        pendingSyncDao.upsertDeletedRunSyncEntity(entity)

        val workRequest = OneTimeWorkRequestBuilder<DeleteRunWorker>()
            .addTag("delete_work")
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .setBackoffCriteria(
                backoffPolicy = BackoffPolicy.EXPONENTIAL,
                backoffDelay = 2000L,
                timeUnit = TimeUnit.MILLISECONDS
            ).setInputData(
                Data.Builder()
                    .putString(DeleteRunWorker.RUN_ID, entity.runId)
                    .build()
            )
            .build()

        applicationScope.launch {
            workManager.enqueue(workRequest).await()
        }.join()

    }

    private suspend fun scheduleFetchRunsWorker(interval: Duration) {
        val isSyncScheduled = withContext(Dispatchers.IO) {
            workManager.getWorkInfosByTag("sync_work").get().isNotEmpty()
        }

        if (isSyncScheduled) {
            return
        }

        val workRequest = PeriodicWorkRequestBuilder<FetchRunWorker>(
            repeatInterval = interval.toJavaDuration()
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(
            // if worker failed then how long will be wait to retry
            // BackoffPolicy.LINEAR try every 2 sec after
            // BackoffPolicy.EXPONENTIAL first 2 secnd, then 8,16,32
            backoffPolicy = BackoffPolicy.EXPONENTIAL,
            backoffDelay = 2000L,
            timeUnit = TimeUnit.MICROSECONDS
        ).setInitialDelay(
            //How long is wait for first execution
            duration = 30, timeUnit = TimeUnit.MINUTES
        ).addTag("sync_work").build()

        workManager.enqueue(workRequest).await()


    }

    override suspend fun cancelAllSyncs() {
        WorkManager.getInstance(context).cancelAllWork().await()
    }

}