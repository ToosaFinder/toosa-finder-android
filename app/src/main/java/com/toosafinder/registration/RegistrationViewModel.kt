package com.toosafinder.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.network.HTTPRes
import com.toosafinder.utils.isEmailValid
import com.toosafinder.utils.isPasswordValid
import com.toosafinder.utils.isUserNameValid
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registrationRepository: RegistrationRepository) : ViewModel(){
    private val _registrationState = MutableLiveData<RegistrationFormState>()
    val registrationFormState: LiveData<RegistrationFormState> = _registrationState

    private val _registrationResult = MutableLiveData<HTTPRes<Unit>>()
    val registrationResult: LiveData<HTTPRes<Unit>> = _registrationResult

    fun registerUser(email: String, login: String, password: String) = viewModelScope.launch {
        _registrationResult.value = registrationRepository.registerUser(email, login, password)
    }

    fun registrationDataChange(email: String, login: String, password: String,
                               passwordConfirmation: String, agreement: Boolean) {
        _registrationState.value = when {
            !isEmailValid(email) -> RegistrationFormState.InvalidEmail
            !isUserNameValid(login) -> RegistrationFormState.InvalidLogin
            !isPasswordValid(password) -> RegistrationFormState.InvalidPassword
            password != passwordConfirmation -> RegistrationFormState.UnequalPasswords
            !agreement -> RegistrationFormState.NoAgreement
            else -> RegistrationFormState.Valid
        }
    }
}