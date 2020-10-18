package com.toosafinder.restorePassword.restorePassword

import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.POST

interface RestorePasswordDataSource {
    companion object {
//        const val restoreURL : String = "/user/resetPassword"
        const val registerURL : String = "/user/setPassword"
    }

//    @POST(restoreURL)
//    fun  restorePassword(email : String) : Call<Void>

    @POST(registerURL)
    fun registerPassword(emailToken : String, password : String) : Deferred<Response<Void>>
}