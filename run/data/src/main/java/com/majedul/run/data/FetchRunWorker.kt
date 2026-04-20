package com.majedul.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.majedul.core.domain.run.RunRepository
import com.majedul.core.domain.util.DataError

class FetchRunWorker(
    context: Context, params: WorkerParameters, private val repository: RunRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }
        return when (val result = repository.fetchRuns()) {
            is com.majedul.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }

            is com.majedul.core.domain.util.Result.Success -> {
                Result.success()
            }
        }

    }
}