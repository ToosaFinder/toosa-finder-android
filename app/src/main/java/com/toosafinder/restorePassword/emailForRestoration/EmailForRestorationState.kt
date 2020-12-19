package com.toosafinder.restorePassword.emailForRestoration

sealed class EmailForRestorationState {
    object Valid : EmailForRestorationState()
    object InvalidEmail : EmailForRestorationState()
}