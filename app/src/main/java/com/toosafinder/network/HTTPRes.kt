package com.toosafinder.network

import android.util.Log
import retrofit2.Response

sealed class HTTPRes<T> {
    data class Success<T>(val data: T): HTTPRes<T>()
    data class Conflict<T>(
        val code: Int,
        val message: String,
        val payload: Any? = null
    ): HTTPRes<T>()
}

fun<T> convertAnswer(result : Response<T>) : HTTPRes<T> {
    Log.d("BEK", "" + result.code() + " " + result.message())
    return when {
        result.code() != 200 -> HTTPRes.Conflict(result.code(), result.message(), result.errorBody())
        else -> HTTPRes.Success(result.body() as T)
    }
}