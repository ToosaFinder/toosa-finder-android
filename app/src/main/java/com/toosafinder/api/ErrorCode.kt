package com.toosafinder.api

import java.lang.IllegalArgumentException

/**
 * Содержит все коды ошибок, которые могут быть получены от сервера
 * Необходимо для валидации ответа сервера и последующей локализации сообщения об ошибке
 */
enum class ErrorCode {
    LOGIN_ERROR,
    LOGIN_DUPLICATION_ERROR,
    EMAIL_DUPLICATION_ERROR,
    EMAIL_TOKEN_EXPIRED,
    EMAIL_TOKEN_NOT_VALID,
    USER_NOT_FOUND;

    companion object {
        fun fromString(string: String): ErrorCode? {
            return try {
                valueOf(string)
            } catch (e: IllegalArgumentException){
                null
            }
        }
    }
}
