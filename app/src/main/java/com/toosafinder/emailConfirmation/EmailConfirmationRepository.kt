package com.toosafinder.emailConfirmation

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmailConfirmationRepository {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://vk.com/").addConverterFactory(
        GsonConverterFactory.create()).build()
    private val emailConfirmationDataSource: EmailConfirmationDataSource = retrofit.create(
        EmailConfirmationDataSource::class.java)

    suspend fun checkEmailToken(emailToken : String) : Response<String> {
        val response = emailConfirmationDataSource.checkEmailToken(emailToken).await()
        return response
    }
}