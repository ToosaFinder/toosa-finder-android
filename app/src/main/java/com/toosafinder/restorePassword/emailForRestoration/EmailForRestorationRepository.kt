package com.toosafinder.restorePassword.emailForRestoration

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmailForRestorationRepository {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://vk.com/").addConverterFactory(
        GsonConverterFactory.create()).build()
    private val emailForRestorationDataSource: EmailForRestorationDataSource = retrofit.create(
        EmailForRestorationDataSource::class.java)

    suspend fun restorePassword (email : String) : Response<Void>{
        return emailForRestorationDataSource.resetPassword(email).await()
    }
}