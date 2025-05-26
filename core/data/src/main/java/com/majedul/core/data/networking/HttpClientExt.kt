package com.majedul.core.data.networking

import com.majedul.core.data.BuildConfig
import com.majedul.core.domain.util.DataError
import com.majedul.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

suspend inline fun <reified Response : Any?> HttpClient.get(
    route: String,
    queryParameter: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall {
        get {
            url {
                constructRoute(route)
                queryParameter.forEach { (key, value) ->
                    parameter(key, value.toString())
                }
            }
        }
    }
}

suspend inline fun <reified Response : Any?> HttpClient.delete(
    route: String,
    queryParameter: Map<String, Any?> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall {
        delete {
            url {
                constructRoute(route)
                queryParameter.forEach { (key, value) ->
                    parameter(key, value.toString())
                }
            }
        }
    }
}

suspend inline fun <reified request, reified Response : Any?> HttpClient.post(
    route: String,
    body: request,
): Result<Response, DataError.Network> {
    return safeCall {
        post {
            url {
                constructRoute(route)
                 setBody(body)
            }
        }
    }
}


suspend inline fun <reified T> safeCall(execute: () -> HttpResponse): Result<T, DataError.Network> {

    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }
    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T, DataError.Network> {
    return when (response.status.value) {

        in 200..299 -> Result.Success(response.body<T>())
        401 -> Result.Error(DataError.Network.UNAUTHORIZED)
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT)
        409 -> Result.Error(DataError.Network.CONFLICT)
        413 -> Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUEST)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN)

    }
}

fun constructRoute(route: String): String {
    return when {
        route.contains(BuildConfig.base_url) -> route
        route.startsWith("/") -> BuildConfig.base_url + route
        else -> BuildConfig.base_url + "/$route"
    }
}