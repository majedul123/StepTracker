package com.majedul.data

import com.majedul.auth.domain.AuthRepository
import com.majedul.core.data.networking.post
import com.majedul.core.domain.util.DataError
import com.majedul.core.domain.util.EmptyResult
import io.ktor.client.HttpClient


class AuthRepositoryImpl(private val httpClient: HttpClient) : AuthRepository {
    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/auth/register",
            body = RegisterRequest(email = email, password = password)
        )
    }
}