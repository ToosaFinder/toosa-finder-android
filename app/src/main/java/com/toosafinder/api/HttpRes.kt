package com.toosafinder.api

/**
 * Содержит результат выполнения запроса к серверу
 * В соответствии с Описанием АПИ сервера, результатов,
 * пригодных для обработки на стороне клиента может быть два: Успешно и Конфликт
 * Если метод апи в случае успешного выполнения не возвращает тело, используйте [UnitHttpRes]
 */
sealed class HttpRes<T> {

    data class Success<T>(val data: T): HttpRes<T>()

    data class Conflict<T>(val error: ErrorRes): HttpRes<T>()

    fun <R> transform(
        onSuccess: (T) -> R,
        onConflict: (ErrorRes) -> R
    ): R = when(this){
            is Success -> onSuccess(data)
            is Conflict -> onConflict(error)
    }
}


/**
 * Специализация [HttpRes] для пустого тела ответа
 */
sealed class UnitHttpRes {
    object Success: UnitHttpRes()
    data class Conflict(val error: ErrorRes): UnitHttpRes()

    fun <R> transform(
        onSuccess: () -> R,
        onConflict: (ErrorRes) -> R
    ): R = when(this){
        is Success -> onSuccess()
        is Conflict -> onConflict(error)
    }
}