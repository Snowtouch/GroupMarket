package com.snowtouch.core.domain.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    data object Loading : Result<Nothing>
    data class Success<out T>(val data : T?) : Result<T>
    data class Failure(val e : Exception) : Result<Nothing>
}

fun <T> Flow<T>.asResult() : Flow<Result<T>> {
    return this
        .map<T, Result<T>> { Result.Success(it) }
        .onStart { emit(Result.Loading) }
        .catch { emit(Result.Failure(Exception(it))) }
}