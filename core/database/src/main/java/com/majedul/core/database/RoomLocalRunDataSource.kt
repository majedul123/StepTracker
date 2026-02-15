package com.majedul.core.database

import android.database.sqlite.SQLiteFullException
import com.majedul.core.database.dao.RunDao
import com.majedul.core.database.mappers.toRun
import com.majedul.core.database.mappers.toRunEntity
import com.majedul.core.domain.run.LocalDataSource
import com.majedul.core.domain.run.Run
import com.majedul.core.domain.run.RunId
import com.majedul.core.domain.util.DataError
import com.majedul.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalRunDataSource(
    private val runDao: RunDao
) : LocalDataSource {

    override fun getRuns(): Flow<List<Run>> {
        return runDao.getRuns().map { runEntities ->
            runEntities.map {
                it.toRun()
            }
        }
    }

    override suspend fun upsertRun(run: Run): Result<RunId, DataError.Local> {
        return try {
            val runEntity = run.toRunEntity()
            runDao.upsertRun(runEntity)
            Result.Success(runEntity.id)
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun upsertRuns(runs: List<Run>): Result<List<RunId>, DataError.Local> {
        return try {
            val entities = runs.map {
                it.toRunEntity()
            }
            runDao.upsertRuns(entities)
            Result.Success(entities.map { it.id })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteRun(id: String) {
        runDao.deleteRun(id)
    }

    override suspend fun deleteAllRuns() {
        runDao.deleteAllRuns()
    }
}