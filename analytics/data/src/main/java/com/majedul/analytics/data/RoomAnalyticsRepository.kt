package com.majedul.analytics.data

import com.majedul.analytics.domain.AnalyticsRepository
import com.majedul.analytics.domain.AnalyticsValues
import com.majedul.core.database.dao.AnalyticsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds

class RoomAnalyticsRepository(
    private val analyticsDao: AnalyticsDao
) : AnalyticsRepository {

    override suspend fun getAnalyticsValue(): AnalyticsValues {

        return withContext(Dispatchers.IO) {

            val totalDistanceRun = async { analyticsDao.getTotalDistance() }
            val totalTimeRun = async { analyticsDao.getTotalTimeRun() }
            val fastestEverRun = async { analyticsDao.getTotalMaxRunSPeed() }
            val avgDistancePerRun = async { analyticsDao.getAvgDistancePerRun() }
            val avgPacePerRun = async { analyticsDao.getAvgSpeedPerRun() }
            AnalyticsValues(
                totalDistanceRun = totalDistanceRun.await(),
                totalTimeRun = totalTimeRun.await().milliseconds,
                fastestEverRun = fastestEverRun.await(),
                avgDistancePerRun = avgDistancePerRun.await(),
                avgPacePerRun = avgPacePerRun.await()
            )
        }
    }
}
