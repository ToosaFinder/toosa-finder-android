package com.toosafinder.restorePassword.emailForRestoration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.network.HTTPRes
import com.toosafinder.utils.isEmailValid
import kotlinx.coroutines.launch

class EmailForRestorationViewModel (private val emailForRestorationRepository: EmailForRestorationRepository): ViewModel() {

    private val _emailConfirmationState = MutableLiveData<EmailConfirmationState>()
    val emailConfirmationState: LiveData<EmailConfirmationState> = _emailConfirmationState

    private val _emailConfirmationResult = MutableLiveData<HTTPRes<Unit>>()
    val emailConfirmationResult: LiveData<HTTPRes<Unit>> = _emailConfirmationResult


    fun sendEmail (email : String) = viewModelScope.launch {
        _emailConfirmationResult.value = emailForRestorationRepository.restorePassword(email)
    }

    fun emailDataChanged(email: String){
        _emailConfirmationState.value = when {
            !isEmailValid(email) -> EmailConfirmationState.InvalidEmail
            else -> EmailConfirmationState.Valid
        }
    }
}