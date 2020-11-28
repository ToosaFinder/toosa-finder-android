package com.toosafinder.restorePassword.restorePassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.api.ErrorCode
import com.toosafinder.utils.UnitOption
import com.toosafinder.utils.isPasswordValid
import com.toosafinder.utils.launchWithErrorLogging

class RestorePasswordViewModel (private val restorePasswordRepository: RestorePasswordRepository) : ViewModel() {

    private val _restorePasswordState = MutableLiveData<RestorePasswordState>()
    val restorePasswordState: LiveData<RestorePasswordState> = _restorePasswordState

    private val _restorePasswordResult = MutableLiveData<UnitOption<ErrorCode?>>()
    val restorePasswordResult: LiveData<UnitOption<ErrorCode?>> = _restorePasswordResult

    fun registerPassword(emailToken: String, password: String) = viewModelScope.launchWithErrorLogging {
        _restorePasswordResult.value = restorePasswordRepository.registerPassword(emailToken, password)
    }

    fun restorePasswordDataChanged(password: String, passwordConfirmation: String){
        _restorePasswordState.value = when{
            !isPasswordValid(password) -> RestorePasswordState.InvalidPassword
            password != passwordConfirmation -> RestorePasswordState.UnequalPasswords
            else -> RestorePasswordState.Valid
        }
    }

}