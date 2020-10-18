package com.toosafinder.login

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
/**
 * Либо этот класс будет переработан так чтобы избавиться от исключений и использовать его во всех модулях,
 * либо для каждого запроса к серверу будет свой такой класс
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}