package com.toosafinder.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.api.ErrorCode
import com.toosafinder.utils.*

class RegistrationViewModel(private val registrationRepository: RegistrationRepository) : ViewModel(){
    private val _registrationState = MutableLiveData<RegistrationFormState>()
    val registrationFormState: LiveData<RegistrationFormState> = _registrationState

    private val _registrationResult = MutableLiveData<UnitOption<ErrorCode?>>()
    val registrationResult: LiveData<UnitOption<ErrorCode?>> = _registrationResult

    fun registerUser(email: String, login: String, password: String) = viewModelScope.launchWithErrorLogging {
        _registrationResult.value = registrationRepository.registerUser(email, login, password)
    }

    fun registrationDataChange(email: String, password: String,
                               passwordConfirmation: String, agreement: Boolean) {
        _registrationState.value = when {
            !isEmailValid(email) -> RegistrationFormState.InvalidEmail
            !isPasswordValid(password) -> RegistrationFormState.InvalidPassword
            password != passwordConfirmation -> RegistrationFormState.UnequalPasswords
            !agreement -> RegistrationFormState.NoAgreement
            else -> RegistrationFormState.Valid
        }
    }
}