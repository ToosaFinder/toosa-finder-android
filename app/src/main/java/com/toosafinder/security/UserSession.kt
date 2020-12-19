package com.toosafinder.security

data class LoggedInUser(
    val name: String,
)

data class UserSessionData(
    val user: LoggedInUser,
    val accessToken: String
)

/**
 * Содержит информацию о текущем пользователе
 * Используется для управления доступом к активити и авторизации запросов к АПИ
 */
class UserSession {

    private var data: UserSessionData? = null

    fun isOpened() = data != null

    fun accessToken(): String? = data?.accessToken

    fun user(): LoggedInUser? = data?.user

    fun open(data: UserSessionData) {
        this.data = data
    }

    fun close() {
        data = null
    }
}
