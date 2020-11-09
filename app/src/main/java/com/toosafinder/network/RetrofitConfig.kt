package com.toosafinder.network

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit


fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://34.67.129.19:8081")
        .client(okHttpClient)
        .addConverterFactory(CustomConverterFactory(jacksonObjectMapper()))
            .build()
}

fun provideOkHttpClient(interceptor: ErrorHandlingInterceptor): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(ErrorHandlingInterceptor())
        .build()
}