package com.toosafinder.registration

import com.toosafinder.api.registration.UserRegistrationReq
import com.toosafinder.network.HTTPRes
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationAPI {
    companion object {
        const val URL: String = "/user/registration"
    }

    @POST(URL)
    suspend fun registerUser(@Body user: UserRegistrationReq) : HTTPRes<Unit>
}