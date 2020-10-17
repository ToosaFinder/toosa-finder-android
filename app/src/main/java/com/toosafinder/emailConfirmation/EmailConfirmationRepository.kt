package com.toosafinder.emailConfirmation

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmailConfirmationRepository {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://vk.com/").addConverterFactory(
        GsonConverterFactory.create()).build()
    private val emailConfirmationDataSource: EmailConfirmationDataSource = retrofit.create(
        EmailConfirmationDataSource::class.java)

    suspend fun checkEmailToken(emailToken : String) : Boolean {
        val call : Call<Boolean> = emailConfirmationDataSource.checkEmailToken(emailToken)
        return call.
    }
}