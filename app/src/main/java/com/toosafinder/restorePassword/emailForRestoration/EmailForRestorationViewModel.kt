package com.toosafinder.restorePassword.emailForRestoration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EmailForRestorationViewModel (private val emailForRestorationRepository: EmailForRestorationRepository): ViewModel() {

    fun sendEmail (email : String) = viewModelScope.launch {
        emailForRestorationRepository.restorePassword(email)
    }


}