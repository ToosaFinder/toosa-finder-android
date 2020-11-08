package com.toosafinder.restorePassword.emailForRestoration

sealed class EmailConfirmationState {
    object Valid : EmailConfirmationState()
    object InvalidEmail : EmailConfirmationState()
}