package com.toosafinder.registration

import android.util.Log
import com.toosafinder.api.registration.UserRegistrationReq
import com.toosafinder.network.HTTPRes
import com.toosafinder.network.convertAnswer

class RegistrationRepository(private val api: RegistrationAPI) {
    suspend fun registerUser(email: String, login: String, password: String) : HTTPRes<Unit> {
        val result = api.registerUser(UserRegistrationReq(email, login, password))
        return convertAnswer(result)
    }
}