package com.toosafinder.restorePassword.emailForRestoration

import com.toosafinder.api.login.PasswordRestoreReq
import com.toosafinder.network.HTTPRes
import retrofit2.http.POST

private const val restoreURL : String = "/user/resetPassword"

interface EmailForRestorationDataSource {

    @POST(restoreURL)
    suspend fun restorePassword(req: PasswordRestoreReq) : HTTPRes<Unit>
}