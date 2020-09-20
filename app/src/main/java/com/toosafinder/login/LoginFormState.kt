package com.toosafinder.login

/**
 * Data validation state of the login form.
 */
sealed class LoginFormState {
        object Valid : LoginFormState()
        class Invalid(val error: Int) : LoginFormState()
}