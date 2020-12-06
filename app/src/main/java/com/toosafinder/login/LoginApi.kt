package com.toosafinder.login

import com.toosafinder.api.login.LoginReq
import com.toosafinder.api.login.LoginRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/user/login")
    suspend fun login(@Body req: LoginReq) : Response<LoginRes>
}