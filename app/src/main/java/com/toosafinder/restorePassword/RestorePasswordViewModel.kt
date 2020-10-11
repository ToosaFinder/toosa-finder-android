package com.toosafinder.restorePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.toosafinder.login.LoginFormState
import com.toosafinder.login.LoginResult

class RestorePasswordViewModel : ViewModel() {

    private val _restorePasswordForm = MutableLiveData<RestorePasswordFormState>()
    val loginFormState: LiveData<RestorePasswordFormState> = _restorePasswordForm

//    private val _loginResult = MutableLiveData<LoginResult>()
//    val loginResult: LiveData<LoginResult> = _loginResult

    fun restorePassword (){

    }
}