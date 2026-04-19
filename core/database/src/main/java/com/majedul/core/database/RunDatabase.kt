package com.majedul.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.majedul.core.database.dao.RunDao
import com.majedul.core.database.dao.RunPendingSyncDao
import com.majedul.core.database.entity.DeleteRunSyncEntity
import com.majedul.core.database.entity.RunEntity
import com.majedul.core.database.entity.RunPendingSyncEntity


@Database(
    entities =
        [
            RunEntity::class,
            RunPendingSyncEntity::class,
            DeleteRunSyncEntity::class
        ],
    version = 2
)

abstract class RunDatabase : RoomDatabase() {
    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao
}