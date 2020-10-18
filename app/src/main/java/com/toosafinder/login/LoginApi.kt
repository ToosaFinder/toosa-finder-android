package com.toosafinder.login

import com.toosafinder.api.login.LoginReq
import com.toosafinder.api.login.LoginRes
import com.toosafinder.network.HTTPRes
import retrofit2.http.Body
import retrofit2.http.POST


interface LoginApi {

    @POST("/user/login")
    suspend fun login(@Body req: LoginReq): HTTPRes<LoginRes>

}