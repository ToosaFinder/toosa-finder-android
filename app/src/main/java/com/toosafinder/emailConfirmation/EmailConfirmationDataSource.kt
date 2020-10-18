package com.toosafinder.emailConfirmation

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface EmailConfirmationDataSource {
    companion object {
        const val URL : String = "kakoi-to URL"
    }

    @GET(URL)
    fun checkEmailToken(emailToken : String) : Deferred<Response<String>>
}