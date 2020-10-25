package com.toosafinder.restorePassword.restorePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.restorePassword.FormState
import kotlinx.coroutines.launch

class RestorePasswordViewModel (val restorePasswordRepository: RestorePasswordRepository) : ViewModel() {

    private val _restorePasswordForm = MutableLiveData<FormState>()
    val loginFormState: LiveData<FormState> = _restorePasswordForm

//    private val _loginResult = MutableLiveData<LoginResult>()
//    val loginResult: LiveData<LoginResult> = _loginResult

    fun registerPassword (emailToken : String, password : String) = viewModelScope.launch{
        restorePasswordRepository.registerPassword(emailToken, password)
    }

    fun passwordMatching(password : String, repeat : String) : Boolean =
        password.equals(repeat)

}