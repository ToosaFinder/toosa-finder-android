package com.toosafinder.network

import android.os.Build
import androidx.annotation.RequiresApi
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.toosafinder.api.ErrorRes
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class CustomConverterFactory(private val objectMapper: ObjectMapper) : Converter.Factory() {

 private val jacksonConverterFactory = JacksonConverterFactory.create(objectMapper)

    @RequiresApi(Build.VERSION_CODES.P)
    override fun responseBodyConverter(
            type: Type,
            annotations: Array<Annotation>,
            retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        type as ParameterizedType
        val returnType = object: TypeReference<Any>(){
            override fun getType() = type.actualTypeArguments.first()
        }
        val defaultConverter = { json: String ->
            objectMapper.readValue(json, returnType)
        }
        val conflictConverter = { json: String ->
            objectMapper.readValue<ErrorRes>(json)
        }
        return ResponseBodyConverter(defaultConverter, conflictConverter)
    }

    override fun requestBodyConverter(
            type: Type,
            parameterAnnotations: Array<Annotation>,
            methodAnnotations: Array<Annotation>,
            retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return jacksonConverterFactory.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit)
    }
}

private class ResponseBodyConverter<T>(
    private val defaultConverter: (String) -> T,
    private val conflictConverter: (String) -> ErrorRes
) : Converter<ResponseBody, HTTPRes<*>> {

    @Throws(IOException::class)
    override fun convert(responseBody: ResponseBody): HTTPRes<*>? {
        val jsonString = responseBody.string()
        return runCatching{
            conflictConverter.invoke(jsonString)
        }.fold(
            onSuccess = {
               HTTPRes.Conflict(it.code, it.message, it.payload)
            },
            onFailure = {
                HTTPRes.Success(defaultConverter.invoke(jsonString))
            }
        )
    }
}

