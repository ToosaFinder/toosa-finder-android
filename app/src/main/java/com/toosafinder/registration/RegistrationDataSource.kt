package com.toosafinder.registration

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

class DTOUser(_email : String, _login : String, _password : String) {
    val email: String = _email
    val login: String = _login
    val password: String = _password
}

interface RegistrationDataSource {
    companion object {
        const val URL: String = "/user/register"
    }

    @POST(URL)
    fun registerUser(@Body user: DTOUser) : Deferred<Response<Void>>
}