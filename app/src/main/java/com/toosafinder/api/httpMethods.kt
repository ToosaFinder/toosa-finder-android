package com.toosafinder.api

import io.ktor.http.HttpMethod.Companion.Delete
import io.ktor.http.HttpMethod.Companion.Get
import io.ktor.http.HttpMethod.Companion.Post
import io.ktor.http.HttpMethod.Companion.Put

suspend inline fun <reified T> ApiClient.post(
    path: String = "",
    body: Any? = null,
    headers: Map<String, String> = mapOf(),
    withAuth: Boolean = true
): HTTPRes<T> = fetch(Post, path, body, headers, withAuth)

suspend inline fun <reified T> ApiClient.get(
    path: String = "",
    body: Any? = null,
    headers: Map<String, String> = mapOf(),
    withAuth: Boolean = true
): HTTPRes<T> = fetch(Get, path, body, headers, withAuth)

suspend inline fun <reified T> ApiClient.put(
    path: String = "",
    body: Any? = null,
    headers: Map<String, String> = mapOf(),
    withAuth: Boolean = true
): HTTPRes<T> = fetch(Put, path, body, headers, withAuth)

suspend inline fun <reified T> ApiClient.delete(
    path: String = "",
    body: Any? = null,
    headers: Map<String, String> = mapOf(),
    withAuth: Boolean = true
): HTTPRes<T> = fetch(Delete, path, body, headers, withAuth)
