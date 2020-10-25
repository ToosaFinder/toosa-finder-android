package com.toosafinder.registration

import com.toosafinder.api.registration.UserRegistrationReq
import com.toosafinder.network.HTTPRes

class RegistrationRepository(private val api: RegistrationAPI) {

    suspend fun registerUser(email: String, login: String, password: String) : HTTPRes<Void> {
        return api.registerUser(UserRegistrationReq(email, login, password))
    }
}