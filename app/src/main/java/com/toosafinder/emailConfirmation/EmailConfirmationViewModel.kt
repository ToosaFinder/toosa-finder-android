package com.toosafinder.emailConfirmation

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EmailConfirmationViewModel (private val emailConfirmationRepository: EmailConfirmationRepository) : ViewModel() {

    private var loginActivityStart : String = "com.toosafinder.login.LoginActivity"

    fun checkEmailToken(emailToken : String, nextActivity : () -> Unit) = viewModelScope.launch{
        val returndToken : String = emailConfirmationRepository.checkEmailToken(emailToken)
        if(returndToken.equals(emailToken))
            val intent : Intent = Intent(loginActivityStart)
            startActivity(intent)
    }
}