package com.toosafinder.restorePassword

sealed class RestorePasswordFormState {
    object Valid : RestorePasswordFormState()
    class Invalid(val error: Int) : RestorePasswordFormState()
}