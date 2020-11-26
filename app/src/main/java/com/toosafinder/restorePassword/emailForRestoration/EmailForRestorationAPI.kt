package com.toosafinder.restorePassword.emailForRestoration

import com.toosafinder.api.login.PasswordRestoreReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailForRestorationAPI {
    companion object {
        const val restoreURL : String = "/user/restore-password"
    }

    @POST(restoreURL)
    suspend fun restorePassword(@Body req: PasswordRestoreReq) : Response<Unit>
}