package com.toosafinder.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.util.*

@KtorExperimentalAPI
fun httpClient() = HttpClient(CIO){
    install(JsonFeature){
        serializer = JacksonSerializer(jacksonObjectMapper())
    }
    disableResponseValidation()
}

private fun HttpClientConfig<*>.disableResponseValidation(){
    HttpResponseValidator {
        validateResponse {  }
    }
}