package com.toosafinder.registration

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegistrationRepository {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://vk.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val registrationDataSource: RegistrationDataSource = retrofit.create(RegistrationDataSource::class.java)

    suspend fun registerUser(user: DTOUser) : Response<Void> {
        return registrationDataSource.registerUser(user).await()
    }
}