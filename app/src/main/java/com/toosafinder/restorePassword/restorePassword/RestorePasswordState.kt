package com.toosafinder.restorePassword.restorePassword

sealed class RestorePasswordState {
    object Valid : RestorePasswordState()
    object InvalidPassword : RestorePasswordState()
    object UnequalPasswords : RestorePasswordState()
}