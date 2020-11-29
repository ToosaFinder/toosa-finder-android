package com.toosafinder.login

import com.toosafinder.api.ApiClient
import com.toosafinder.api.ErrorCode
import com.toosafinder.api.login.LoginReq
import com.toosafinder.api.login.LoginRes
import com.toosafinder.api.post
import com.toosafinder.utils.Option
import com.toosafinder.security.LoggedInUser
import com.toosafinder.security.UserSession
import com.toosafinder.security.UserSessionData

class LoginRepository(
    private val apiClient: ApiClient,
    private val userSession: UserSession
) {

    suspend fun login(user: String, password: String): Option<LoggedInUser, ErrorCode?> =
        apiClient.post<LoginRes>("/user/login", LoginReq(user, password), withAuth = false)
            .transform(
                onSuccess = {
                    userSession.open(UserSessionData(LoggedInUser(user), it.accessToken))
                    Option.success(LoggedInUser(user))
                },
                onConflict = { Option.error(ErrorCode.fromString(it.code)) }
            )
}
