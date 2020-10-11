package com.toosafinder.restorePassword

import retrofit2.http.POST

interface RestorePasswordDataSource {
    companion object {
        val URL : String = "/user/resetPassword"
    }

    @POST
    fun restorePassword(email : String)

    @POST
    fun registerPassword(emailToken : String, password : String)
}