package com.toosafinder.restorePassword

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestorePasswordRepository {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://vk.com/").addConverterFactory(
        GsonConverterFactory.create()).build()
    private val restorePasswordDataSource: RestorePasswordDataSource = retrofit.create(RestorePasswordDataSource::class.java)

    fun restorePassword (email : String) {
        restorePasswordDataSource.restorePassword(email)
    }

    fun registerPassword(emailToken : String, password : String) {
        restorePasswordDataSource.registerPassword(emailToken, password)
    }
}