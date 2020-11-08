package com.toosafinder.restorePassword.emailForRestoration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.login.LoginFormState
import com.toosafinder.login.LoginResult
import com.toosafinder.restorePassword.FormState
import com.toosafinder.restorePassword.SendingResult
import kotlinx.coroutines.launch

class EmailForRestorationViewModel (private val emailForRestorationRepository: EmailForRestorationRepository): ViewModel() {

    private val _loginForm = MutableLiveData<FormState>()
    val loginFormState: LiveData<FormState> = _loginForm

    private val _sendingResult = MutableLiveData<SendingResult>()
    val loginResult: LiveData<SendingResult> = _sendingResult

    fun sendEmail (email : String) = viewModelScope.launch {
        val response = emailForRestorationRepository.restorePassword(email)
        when(response.code()){
            409 -> throw Exception("User not found")
        }
    }


}