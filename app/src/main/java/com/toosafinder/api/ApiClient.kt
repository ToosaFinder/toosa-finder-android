package com.toosafinder.api

import android.util.Log
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import kotlin.text.Charsets.UTF_8

const val AUTHORIZATION_HEADER = "Authorization"
const val ACCESS_TOKEN_PREFIX = "Bearer "

/**
 * Http-Клиент, адаптированный под наш бекенд
 * Авторизация запросов, формат ошибок поддерживаются
 */
//TODO: Тут все поля и методы получились публичными. Это нужно чтобы inline работал.
// Если есть идеи как немного прикрыть этот класс, предлагайте
class ApiClient(
    val http: HttpClient,
    val baseUrl: String,
    val tokenProvider: () -> String
) {

    //val mapper = jacksonObjectMapper()
    val mapper = jsonMapper{
        addModule(kotlinModule())
        addModule(JavaTimeModule())
    }

    suspend inline fun <reified T> fetch(
        method: HttpMethod,
        path: String,
        body: Any?,
        headers: Map<String, String>,
        withAuth: Boolean = true
    ): HttpRes<T> {
        Log.d("Http", "trying to send request: $path, $method, $body")
        val response = httpCall(method, path, body, headers, withAuth)
        val responseBody = response.readText(UTF_8)
        return deserializeHttpRes(response.status.value, responseBody)
            ?: error("received error response: $response, body: $responseBody")
    }
    
    suspend fun fetchUnit(
        method: HttpMethod,
        path: String,
        body: Any?,
        headers: Map<String, String>,
        withAuth: Boolean = true
    ): UnitHttpRes {
        val response = httpCall(method, path, body, headers, withAuth)
        val responseBody = response.readText(UTF_8)
        return deserializeEmptyHttpRes(response.status.value, responseBody)
            ?: error("received error response: $response, body: $responseBody")
    }

    private fun deserializeEmptyHttpRes(status: Int, body: String): UnitHttpRes? =
        when(status) {
            200 -> UnitHttpRes.Success
            409 -> UnitHttpRes.Conflict(mapper.readValue(body))
            else -> null
        }

    inline fun <reified T> deserializeHttpRes(status: Int, body: String): HttpRes<T>? =
        when(status) {
            200 -> HttpRes.Success(mapper.readValue(body))
            409 -> HttpRes.Conflict(mapper.readValue(body))
            else -> null
        }

    suspend fun httpCall(method: HttpMethod, path: String, body: Any?,
                         headers: Map<String, String>, withAuth: Boolean = true
    ): HttpResponse {
        Log.d("Http", "trying to send request: $path, $method, $body")
        return http.request<HttpStatement>("$baseUrl/${path.trimStart('/')}") {
            contentType(Json)
            body?.let { this.body = body }
            this.method = method
            this.headers {
                headers.forEach(this::append)
                if (withAuth) {
                    this.append(AUTHORIZATION_HEADER, ACCESS_TOKEN_PREFIX + tokenProvider())
                }
            }
        }.execute()
    }}