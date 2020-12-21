package com.toosafinder.login


sealed class LoginFormState {
        object Valid : LoginFormState()
        object InvalidLogin : LoginFormState()
        object InvalidPassword : LoginFormState()
}