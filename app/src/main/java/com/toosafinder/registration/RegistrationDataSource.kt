package com.toosafinder.registration

import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationDataSource {
    companion object {
        const val URL: String = "/user/register"
    }

    @POST(URL)
    fun registerUser(email : String, login : String, password : String)
}