package com.toosafinder.restorePassword.emailForRestoration

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.POST

interface EmailForRestorationDataSource {
    companion object {
        const val restoreURL : String = "/user/resetPassword"
    }

    @POST(restoreURL)
    fun resetPassword(emailToken : String) : Deferred<Response<Void>>
}