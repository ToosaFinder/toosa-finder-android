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

    private val _emailForRestorationState = MutableLiveData<EmailForRestorationState>()
    val emailCForRestorationState: LiveData<EmailForRestorationState> = _emailForRestorationState

    private val _emailForRestorationResult = MutableLiveData<UnitOption<ErrorCode?>>()
    val emailForRestorationResult: LiveData<UnitOption<ErrorCode?>> = _emailForRestorationResult

    fun sendEmail (email : String) = viewModelScope.launchWithErrorLogging {
        _emailForRestorationResult.value = emailForRestorationRepository.restorePassword(email)
    }

    fun emailDataChanged(email: String){
        _emailForRestorationState.value = when {
            !isEmailValid(email) -> EmailForRestorationState.InvalidEmail
            else -> EmailForRestorationState.Valid
        }
    }
}