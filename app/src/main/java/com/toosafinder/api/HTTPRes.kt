package com.toosafinder.api

/**
 * Содержит результат выполнения запроса к серверу
 * В соответствии с Описанием АПИ сервера, результатов,
 * пригодных для обработки на стороне клиента может быть два: Успешно и Конфликт
 */
sealed class HTTPRes<T> {
    data class Success<T>(val data: T): HTTPRes<T>()

    data class Conflict<T>(val error: ErrorRes): HTTPRes<T>()

    fun <R> transform(
        onSuccess: (T) -> R,
        onConflict: (ErrorRes) -> R
    ): R = when(this){
            is Success -> onSuccess(data)
            is Conflict -> onConflict(error)
    }
}
