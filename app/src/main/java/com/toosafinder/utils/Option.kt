package com.toosafinder.utils

/**
 * Содержит результат выполнения операции: Success или Error
 */
sealed class Option<T, E> {

    data class Success<T, E>(val data: T) : Option<T, E>()
    data class Error<T, E>(val error: E) : Option<T, E>()

    companion object {
        inline fun <reified T, reified E> success(data: T) = Success<T, E>(data)

        inline fun <reified T, reified E> error(error: E) = Error<T, E>(error)
    }

    inline fun <reified R> finalize(onSuccess: (T) -> R, onError: (E) -> R): R =
        when(this){
            is Success -> onSuccess(data)
            is Error -> onError(error)
        }

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[data=$error]"
        }
    }
}

/**
 * Трансформирует значение, если успех.
 * В случае ошибки оставляет значение прежним
 */
inline fun <reified R, reified T, reified E> Option<T,E>.mapSuccess(transform: (T) -> R): Option<R, E> =
    when(this) {
        is Option.Success -> Option.success(transform(data))
        is Option.Error -> Option.error(this.error)
    }

/**
 * Трансформирует значение, если ошибка.
 * В случае успеха оставляет значение прежним
 */
inline fun <reified R, reified T, reified E> Option<T,E>.mapError(transform: (E) -> R): Option<T, R> =
    when(this) {
        is Option.Success -> Option.success(data)
        is Option.Error -> Option.error(transform(error))
    }
