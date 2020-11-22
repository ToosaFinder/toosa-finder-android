package com.toosafinder.restorePassword.restorePassword

import com.toosafinder.api.login.PasswordSetReq
import com.toosafinder.network.HTTPRes
import retrofit2.http.POST

private const val registerURL : String = "/user/setPassword"

interface RestorePasswordDataSource {

    @POST(registerURL)
    suspend fun registerPassword(req : PasswordSetReq) : HTTPRes<Unit>
}