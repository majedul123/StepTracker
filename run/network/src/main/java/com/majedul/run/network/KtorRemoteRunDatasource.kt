package com.majedul.run.network

import com.majedul.core.data.networking.delete
import com.majedul.core.data.networking.get
import com.majedul.core.data.networking.safeCall
import com.majedul.core.domain.run.RemoteRunDataSource
import com.majedul.core.domain.run.Run
import com.majedul.core.domain.util.DataError
import com.majedul.core.domain.util.EmptyResult
import com.majedul.core.domain.util.Result
import com.majedul.core.domain.util.map
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KtorRemoteRunDatasource(
    private val httpClient: HttpClient
) : RemoteRunDataSource {

    override suspend fun getRuns(): Result<List<Run>, DataError.Network> {
        return httpClient.get<List<RunDto>>(
            route = "/runs"
        ).map { runDtos ->
            runDtos.map { it.toRun() }
        }
    }

    override suspend fun postRun(run: Run, mapPicture: ByteArray): Result<Run, DataError.Network> {

        val createRunRequestJson = Json.encodeToString(run.toCreateRunRequest())
        val result = safeCall<RunDto> {
            httpClient.submitFormWithBinaryData(
                url = "/run", formData = formData {
                    append("MAP_PICTURE", mapPicture, Headers.build {
                        append(HttpHeaders.ContentType, "image/jpg")
                        append(HttpHeaders.ContentDisposition, "filename=mappicture.jpg")
                    })

                    append("RUN_DATA", createRunRequestJson, Headers.build {
                        append(HttpHeaders.ContentType, "application/json")
                        append(HttpHeaders.ContentDisposition, "form-data:\"RUN_DATA\"")
                    })
                }

            ) {
                method = HttpMethod.Post
            }
        }

        return result.map {
            it.toRun()
        }
    }

    override suspend fun deleteRun(id: String): EmptyResult<DataError.Network> {
        return httpClient.delete(
            route = "/run", queryParameters = mapOf("id" to id)
        )
    }
}