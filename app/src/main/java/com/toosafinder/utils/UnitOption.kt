package com.toosafinder.utils


/**
 * Содержит результат выполнения операции: Success или Error
 * В отличие от [Option], Success не содержит данных, а просто указывает на то,
 * что операция выполнена успешно
 */
sealed class UnitOption<E> {
    class Success<E>: UnitOption<E>()
    data class Error<E>(val error: E): UnitOption<E>()

    companion object {
        inline fun <reified E> success() = Success<E>()

        inline fun <reified E> error(error: E) = Error(error)
    }

    override fun toString() : String = when (this) {
        is Success -> "Success"
        is Error -> "Error[data=$error]"
    }

    inline fun <reified R> finalize(onSuccess: () -> R, onError: (E) -> R){
        when(this){
            is Success -> onSuccess()
            is Error -> onError(error)
        }
    }
}

inline fun <reified R, reified E> UnitOption<E>.mapError(transform: (E) -> R): UnitOption<R> =
    when(this) {
        is UnitOption.Success -> UnitOption.success()
        is UnitOption.Error -> UnitOption.error(transform(error))
    }
