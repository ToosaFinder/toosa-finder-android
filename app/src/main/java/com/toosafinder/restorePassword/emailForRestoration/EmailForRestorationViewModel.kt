package com.toosafinder.restorePassword.emailForRestoration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.api.ErrorCode
import com.toosafinder.utils.UnitOption
import com.toosafinder.utils.isEmailValid
import com.toosafinder.utils.launchWithErrorLogging

class EmailForRestorationViewModel(
    private val emailForRestorationRepository: EmailForRestorationRepository
): ViewModel() {

    private val _emailConfirmationState = MutableLiveData<EmailConfirmationState>()
    val emailConfirmationState: LiveData<EmailConfirmationState> = _emailConfirmationState

    private val _emailConfirmationResult = MutableLiveData<UnitOption<ErrorCode?>>()
    val emailConfirmationResult: LiveData<UnitOption<ErrorCode?>> = _emailConfirmationResult

    fun sendEmail (email : String) = viewModelScope.launchWithErrorLogging {
        _emailConfirmationResult.value = emailForRestorationRepository.restorePassword(email)
    }

    fun emailDataChanged(email: String){
        _emailConfirmationState.value = when {
            !isEmailValid(email) -> EmailConfirmationState.InvalidEmail
            else -> EmailConfirmationState.Valid
        }
    }
}