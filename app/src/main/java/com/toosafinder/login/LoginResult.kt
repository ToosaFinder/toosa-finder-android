package com.toosafinder.login

/**
 * Authentication result : success (user details) or error message.
 */
sealed class LoginResult {
        data class Success(val loggedInUserView: LoggedInUserView): LoginResult()
        data class Error(var code: Int, val error: String?): LoginResult()
}