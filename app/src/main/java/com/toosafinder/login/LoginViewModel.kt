package com.toosafinder.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.toosafinder.R
import com.toosafinder.utils.isPasswordValid
import com.toosafinder.utils.isUserNameValid
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
}