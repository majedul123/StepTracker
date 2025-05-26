package com.majedul.core.domain.util

sealed interface DataError : Error {

    enum class Network : DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        CONFLICT,
        NO_INTERNET,
        PAYLOAD_TOO_LARGE,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN,
        TOO_MANY_REQUEST
    }

    enum class Local: DataError {
        DISK_FULL,

    }
}
