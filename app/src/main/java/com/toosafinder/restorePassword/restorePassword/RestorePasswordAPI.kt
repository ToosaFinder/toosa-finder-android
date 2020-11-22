package com.toosafinder.restorePassword.restorePassword

import com.toosafinder.api.login.PasswordSetReq
import com.toosafinder.network.HTTPRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

private const val registerURL : String = "/user/set-password"

interface RestorePasswordAPI {

    @POST(registerURL)
    suspend fun registerPassword(@Body req : PasswordSetReq) : Response<Unit>
}