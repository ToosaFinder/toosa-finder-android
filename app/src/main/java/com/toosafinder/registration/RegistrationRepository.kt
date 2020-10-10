package com.toosafinder.registration

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistrationRepository {
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://vk.com/").addConverterFactory(GsonConverterFactory.create()).build()
    private val registrationDataSource: RegistrationDataSource = retrofit.create(RegistrationDataSource::class.java)

    fun registerUser(email : String, login : String, password : String) {
        registrationDataSource.registerUser(email, login, password)
    }
}