package com.dvt.greensys.weather.app.core.common.result

sealed interface Result<out T> {
    data class Success<T>(val data: T) : Result<T>
    data class Error(val error: Throwable) : Result<Nothing>
    data object Loading : Result<Nothing>
}

inline fun <T, R> T.runHandling(block: T.() -> R): Result<R> = try {
    Result.Success(data = block())
} catch (e: Throwable) {
    Result.Error(error = e)
}
