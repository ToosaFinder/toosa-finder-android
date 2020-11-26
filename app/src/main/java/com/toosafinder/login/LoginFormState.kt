package com.toosafinder.login

/**
 * Data validation state of the login form.
 */
sealed class LoginFormState {
        object Valid : LoginFormState()
        object InvalidLogin : LoginFormState()
        object InvalidPassword : LoginFormState()
}