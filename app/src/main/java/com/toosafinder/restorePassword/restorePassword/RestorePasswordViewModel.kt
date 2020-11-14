package com.toosafinder.restorePassword.restorePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.network.HTTPRes
import com.toosafinder.utils.isPasswordValid
import kotlinx.coroutines.launch

class RestorePasswordViewModel (val restorePasswordRepository: RestorePasswordRepository) : ViewModel() {

    private val _restorePasswordState = MutableLiveData<RestorePasswordState>()
    val restorePasswordState: LiveData<RestorePasswordState> = _restorePasswordState

    private val _restorePasswordResult = MutableLiveData<HTTPRes<Unit>>()
    val restorePasswordResult: LiveData<HTTPRes<Unit>> = _restorePasswordResult

    fun registerPassword (emailToken : String, password : String) = viewModelScope.launch{
        _restorePasswordResult.value = restorePasswordRepository.registerPassword(emailToken, password)
    }

    fun prestorePasswordDataChanged(emailToken : String, password : String, passwordConfirmation: String){
        _restorePasswordState.value = when{
            !isPasswordValid(password) -> RestorePasswordState.InvalidPassword
            password != passwordConfirmation -> RestorePasswordState.UnequalPasswords
            else -> RestorePasswordState.Valid
        }
    }

}