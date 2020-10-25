package com.toosafinder.login

import com.toosafinder.api.login.LoginReq
import com.toosafinder.network.HTTPRes


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

    suspend fun login(username: String, password: String): LoggedInUser? {
        return when(val res = api.login(LoginReq(username, password))){
            is HTTPRes.Success -> {
                user = LoggedInUser(username)
                accessToken = res.data.accessToken
                user
            }
            is HTTPRes.Conflict -> null
        }
    }
}