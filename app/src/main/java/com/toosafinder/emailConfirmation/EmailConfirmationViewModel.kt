package com.toosafinder.emailConfirmation

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class EmailConfirmationViewModel (private val emailConfirmationRepository: EmailConfirmationRepository) : ViewModel() {

    private var loginActivityStart : String = "com.toosafinder.login.LoginActivity"

    fun checkEmailToken(emailToken : String, nextActivity : () -> Unit) = viewModelScope.launch{
        val responseAnswer : Response<String> = emailConfirmationRepository.checkEmailToken(emailToken)
        val returnedToken : String = responseAnswer.message()
        if(returnedToken.equals(emailToken))
            nextActivity()
    }
}