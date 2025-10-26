package com.majedul.auth.data

import com.majedul.auth.domain.AuthRepository
import com.majedul.core.data.networking.post
import com.majedul.core.domain.AuthInfo
import com.majedul.core.domain.SessionStorage
import com.majedul.core.domain.util.DataError
import com.majedul.core.domain.util.EmptyResult
import com.majedul.core.domain.util.Result
import com.majedul.core.domain.util.asEmptyDataResult
import io.ktor.client.HttpClient

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionStorage: SessionStorage
): AuthRepository {

    override suspend fun register(email: String, password: String): EmptyResult<DataError.Network> {
        return httpClient.post<RegisterRequest, Unit>(
            route = "/register",
            body = RegisterRequest(
                email = email,
                password = password
            )
        )
    }

    override suspend fun login(
        email: String, password: String
    ): EmptyResult<DataError.Network> {
        val result = httpClient.post<LoginRequest, LoginResponse>(
            route = "/login",
            body = LoginRequest(
                email = email,
                password = password
            )
        )

        if(result is Result.Success){
            val authInfo = AuthInfo(
                accessToken = result.data.accessToken,
                refreshToken = result.data.refreshToken,
                userId = result.data.userId,
            )
            sessionStorage.set(authInfo)
        }
        return  result.asEmptyDataResult()
    }
}