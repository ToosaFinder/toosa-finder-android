package com.toosafinder.api

import android.util.Log
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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
class ApiClient(
    val http: HttpClient,
    val baseUrl: String,
    val tokenProvider: () -> String
) {

    val mapper = jacksonObjectMapper()

    suspend inline fun <reified T> fetch(
        method: HttpMethod,
        path: String,
        body: Any?,
        headers: Map<String, String>,
        withAuth: Boolean = true
    ): HTTPRes<T> {
        Log.d("Http","trying to send request: $path, $method, $body")
        val statement = http.request<HttpStatement>("$baseUrl/${path.trimStart('/')}") {
            contentType(Json)
            body?.let { this.body = body }
            this.method = method
            this.headers {
                headers.forEach(this::append)
                if (withAuth) {
                    this.append(AUTHORIZATION_HEADER, ACCESS_TOKEN_PREFIX + tokenProvider())
                }
            }
        }
        val response = statement.execute()
        val responseBody = response.readText(UTF_8)
        Log.d("Http","received response: ${response.status}, ${responseBody}}")
        return when(response.status.value) {
            200 -> HTTPRes.Success(mapper.readValue(responseBody))
            409 -> HTTPRes.Conflict(mapper.readValue(responseBody))
            else -> error("received error response: $response, body: $responseBody")
        }
    }
}