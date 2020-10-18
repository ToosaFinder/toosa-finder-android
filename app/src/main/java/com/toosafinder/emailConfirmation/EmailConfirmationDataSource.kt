package com.toosafinder.emailConfirmation

import retrofit2.Call
import retrofit2.http.GET

interface EmailConfirmationDataSource {
    companion object {
        const val URL : String = "kakoi-to URL"
    }

    @GET(URL)
    fun checkEmailToken(emailToken : String) : Call<String>
}