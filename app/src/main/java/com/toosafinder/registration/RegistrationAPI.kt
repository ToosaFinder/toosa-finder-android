package com.toosafinder.registration

import com.toosafinder.api.registration.UserRegistrationReq
import com.toosafinder.network.HTTPRes
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationAPI {
    companion object {
        const val URL: String = "/user/register"
    }

    @POST(URL)
    suspend fun registerUser(@Body user: UserRegistrationReq) : HTTPRes<Void>
}