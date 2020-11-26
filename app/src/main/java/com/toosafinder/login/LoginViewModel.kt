package com.toosafinder.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.network.HTTPRes

import com.toosafinder.utils.isPasswordValid
import com.toosafinder.utils.isUserNameValid
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) = viewModelScope.launch {
        val res = loginRepository.login(username, password)
        _loginResult.value = when(res) {
            is HTTPRes.Success -> LoginResult.Success(LoggedInUserView(username))
            is HTTPRes.Conflict -> LoginResult.Error(res.code, res.message)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        _loginForm.value = when {
            !isUserNameValid(username) -> LoginFormState.InvalidLogin
            !isPasswordValid(password) -> LoginFormState.InvalidPassword
            else -> LoginFormState.Valid
        }
    }
}