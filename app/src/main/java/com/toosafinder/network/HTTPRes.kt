package com.toosafinder.network

import android.util.Log
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.toosafinder.api.ErrorRes
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

sealed class HTTPRes<T> {
    data class Success<T>(val data: T): HTTPRes<T>()
    data class Conflict<T>(
        val code: Int,
        val message: String?,
        val payload: Any? = null
    ): HTTPRes<T>()
}

fun<T> convertAnswer(result : Response<T>) : HTTPRes<T> {
    val error = result.errorBody()?.string()
    Log.d("BEK", "" + result.code() + " " + error)
    val conflictConverter = { json: String? ->
        try {
            if(json != null) {
                jacksonObjectMapper().readValue(json)
            } else {
                ErrorRes(result.code().toString(), null, null)
            }
        } catch (e: Exception) {
            ErrorRes(result.code().toString(), null, null)
        }
    }
    val errorRes = conflictConverter(error)
    return when {
        !result.isSuccessful -> HTTPRes.Conflict(result.code(),
            if(errorRes.message == null) errorRes.code else errorRes.message, result.errorBody())
        else -> HTTPRes.Success(result.body() as T)
    }
}