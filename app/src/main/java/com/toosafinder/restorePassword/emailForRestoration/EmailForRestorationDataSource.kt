package com.toosafinder.restorePassword.emailForRestoration

import com.toosafinder.api.login.PasswordRestoreReq
import com.toosafinder.network.HTTPRes
import retrofit2.http.Body
import retrofit2.http.POST

private const val restoreURL : String = "/user/restore-password"

interface EmailForRestorationDataSource {

    @POST(restoreURL)
    suspend fun restorePassword(@Body req: PasswordRestoreReq) : HTTPRes<Unit>
}