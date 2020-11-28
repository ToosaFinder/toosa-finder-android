package com.toosafinder.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.api.ErrorCode
import com.toosafinder.utils.*

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<Option<LoggedInUserView, ErrorCode?>>()
    val loginResult: LiveData<Option<LoggedInUserView, ErrorCode?>> = _loginResult

    fun login(username: String, password: String) = viewModelScope.launchWithErrorLogging {
        _loginResult.value = loginRepository.login(username, password)
            .mapSuccess {LoggedInUserView(it.name)}
    }

    fun loginDataChanged(username: String, password: String) {
        _loginForm.value = when {
            !isUserNameValid(username) -> LoginFormState.InvalidLogin
            !isPasswordValid(password) -> LoginFormState.InvalidPassword
            else -> LoginFormState.Valid
        }
    }
}
