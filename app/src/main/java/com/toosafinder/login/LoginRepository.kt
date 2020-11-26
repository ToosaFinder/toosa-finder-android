package com.toosafinder.login

import com.toosafinder.api.login.LoginReq
import com.toosafinder.api.login.LoginRes
import com.toosafinder.network.HTTPRes
import com.toosafinder.network.convertAnswer


class LoginRepository(private val api: LoginApi) {

    var user: LoggedInUser? = null
        private set

    private var accessToken: String? = null

    val isLoggedIn: Boolean
        get() = user != null && accessToken != null

    fun logout() {
        user = null
        accessToken = null
    }

    suspend fun login(username: String, password: String): HTTPRes<LoginRes> {
        val res = api.login(LoginReq(username, password))
        return when(val httpRes = convertAnswer(res)){
            is HTTPRes.Success -> {
                user = LoggedInUser(username)
                accessToken = httpRes.data.accessToken
                httpRes
            }
            else -> httpRes
        }
    }
}