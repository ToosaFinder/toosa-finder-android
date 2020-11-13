package com.toosafinder.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://34.67.129.19:8081")
        .client(okHttpClient)
        //.addConverterFactory(CustomConverterFactory(jacksonObjectMapper()))
        .addConverterFactory(GsonConverterFactory.create())
            .build()
}

fun provideOkHttpClient(interceptor: ErrorHandlingInterceptor): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(interceptor)
        .build()
}