package com.snowtouch.core.domain.model

sealed class Response<out T> {
    object Loading: com.snowtouch.core.domain.model.Response<Nothing>()
    data class Success<out T>(val data: T?): com.snowtouch.core.domain.model.Response<T>()
    data class Failure(val e: Exception): com.snowtouch.core.domain.model.Response<Nothing>()
}