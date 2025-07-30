package com.majedul.core.domain.util

sealed interface Result<out D, out E : Error> {/*
    out D -> Generic arguments for type of data
    out E -> Generic arguments for type of error
    */

    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.majedul.core.domain.util.Error>(val error: E) : Result<Nothing, E>

}

enum class PasswordValidationError : Error {
    TOO_SHORT, MISSING_ONE_DIGIT
}

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {

    /*
        T-> Data types
        E-> Error type
        R -> Data type that we want to map previous data type
     */

    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map {  }
}

typealias EmptyResult<E> = Result<Unit, E>
/*
fun validatePassword(password: String): Result<Unit, PasswordValidationError> {
    //logic
    return Result.Error(PasswordValidationError.TOO_SHORT)

}

fun handleError(result: Result<Unit, PasswordValidationError>) {
    when (result) {

        is Result.Success -> {
            // valid password
        }

        is Result.Error -> {
            when (result.error) {
                PasswordValidationError.TOO_SHORT -> TODO()
                PasswordValidationError.MISSING_ONE_DIGIT -> TODO()
            }
        }
    }


}*/
