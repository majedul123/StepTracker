package com.majedul.core.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.microseconds

object Timer {

    fun timeAndEmit(): Flow<Duration> {
        return flow {
            var lastTimeEmits = System.currentTimeMillis()

            while (true) {
                delay(200L)
                val currentTime = System.currentTimeMillis()
                val elapsedTime = currentTime - lastTimeEmits
                emit(elapsedTime.microseconds)
                lastTimeEmits = currentTime
            }
        }
    }

}