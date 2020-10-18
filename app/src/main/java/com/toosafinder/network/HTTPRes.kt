package com.toosafinder.network

sealed class HTTPRes<T> {
    data class Success<T>(val data: T): HTTPRes<T>()
    data class Conflict<T>(
        val code: String,
        val message: String? = null,
        val payload: Any? = null
    ): HTTPRes<T>()
}