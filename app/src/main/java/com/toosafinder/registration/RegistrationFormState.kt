package com.toosafinder.registration

sealed class RegistrationFormState {
    object Valid : RegistrationFormState()
    object InvalidEmail : RegistrationFormState()
    object InvalidLogin : RegistrationFormState()
    object UnequalPasswords : RegistrationFormState()
    object InvalidPassword : RegistrationFormState()
}