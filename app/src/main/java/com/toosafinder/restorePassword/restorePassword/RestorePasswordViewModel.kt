package com.toosafinder.restorePassword.restorePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toosafinder.restorePassword.FormState

class RestorePasswordViewModel : ViewModel() {

    private val _restorePasswordForm = MutableLiveData<FormState>()
    val loginFormState: LiveData<FormState> = _restorePasswordForm

//    private val _loginResult = MutableLiveData<LoginResult>()
//    val loginResult: LiveData<LoginResult> = _loginResult

    fun restorePassword (){

    }
}