package com.toosafinder.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope

import com.toosafinder.R
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) = viewModelScope.launch {
        _loginResult.value = loginRepository.login(username, password)
            ?. let { LoginResult.Success(loggedInUserView = LoggedInUserView(displayName = it.name)) }
            ?: LoginResult.Error(error = R.string.login_failed)
    }

    fun loginDataChanged(username: String, password: String) {
        _loginForm.value = when {
            !isUserNameValid(username) -> LoginFormState.Valid
            !isPasswordValid(password) -> LoginFormState.Invalid(error = R.string.action_sign_in)
            else -> LoginFormState.Valid
        }
    }

    private fun isUserNameValid(username: String): Boolean =
        if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }

    private fun isPasswordValid(password: String): Boolean =
        password.length > 5
}