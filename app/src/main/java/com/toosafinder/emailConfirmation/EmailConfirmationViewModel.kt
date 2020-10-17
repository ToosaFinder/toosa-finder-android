package com.toosafinder.emailConfirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EmailConfirmationViewModel (private val emailConfirmationRepository: EmailConfirmationRepository) : ViewModel() {

    fun checkEmailToken(emailToken : String) = viewModelScope.launch{
        emailConfirmationRepository.checkEmailToken(emailToken)
    }
}