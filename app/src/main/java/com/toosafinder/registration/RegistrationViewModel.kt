package com.toosafinder.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.utils.isEmailValid
import com.toosafinder.utils.isPasswordValid
import com.toosafinder.utils.isUserNameValid
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registrationRepository: RegistrationRepository) : ViewModel(){
    private val _registrationState = MutableLiveData<RegistrationFormState>()
    val registrationFormState: LiveData<RegistrationFormState> = _registrationState

    fun registerUser(user: DTOUser) = viewModelScope.launch {
        registrationRepository.registerUser(user)
    }

    fun registrationDataChange(email: String, login: String, password: String, passwordConfirmation: String) {
        _registrationState.value = com.toosafinder.registration.registrationDataChange(email, login, password, passwordConfirmation)
    }
}

fun registrationDataChange(email: String, login: String, password: String, passwordConfirmation: String): RegistrationFormState {
    return when {
        !isEmailValid(email) -> RegistrationFormState.InvalidEmail
        !isUserNameValid(login) -> RegistrationFormState.InvalidLogin
        !isPasswordValid(password) -> RegistrationFormState.InvalidPassword
        password != passwordConfirmation -> RegistrationFormState.UnequalPasswords
        else -> RegistrationFormState.Valid
    }
}