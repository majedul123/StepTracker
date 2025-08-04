package com.majedul.auth.domain

import com.majedul.core.domain.util.DataError
import com.majedul.core.domain.util.EmptyResult

interface AuthRepository {
    suspend fun register(email: String, password: String): EmptyResult<DataError.Network>
}