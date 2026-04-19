package com.majedul.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.majedul.core.database.entity.DeleteRunSyncEntity
import com.majedul.core.database.entity.RunPendingSyncEntity

@Dao
interface RunPendingSyncDao {

    //CREATED RUNS

    @Query("SELECT * FROM runpendingsyncentity WHERE userId = :userId")
    suspend fun getAllRunPendingSyncEntities(userId: String): List<RunPendingSyncEntity>

    @Query("SELECT * FROM runpendingsyncentity WHERE runId=:runId")
    suspend fun getRunPendingSyncEntity(runId: String): RunPendingSyncEntity?

    @Upsert
    suspend fun upsertRunPendingSyncEntity(entity: RunPendingSyncEntity)

    @Query("DELETE FROM runpendingsyncentity where runId=:runId")
    suspend fun deleteRunPendingSyncEntity(runId: String)


    //DELETED RUNS

    @Query("SELECT * FROM  deleterunsyncentity WHERE userId=:userId")
    suspend fun getAllDeletedRunSyncEntities(userId: String): List<DeleteRunSyncEntity>

    @Upsert
    suspend fun upsertDeletedRunSyncEntity(entity: DeleteRunSyncEntity)

    @Query("DELETE FROM deleterunsyncentity WHERE runId=:runId")
    suspend fun deleteDeletedRunSyncEntity(runId: String)
}