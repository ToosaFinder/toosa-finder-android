package com.toosafinder.emailConfirmation

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*

class EmailConfirmationViewModel (private val emailConfirmationRepository: EmailConfirmationRepository) : ViewModel() {

    private var loginActivityStart : String = "com.toosafinder.login.LoginActivity"

    fun checkEmailToken(emailToken : UUID, nextActivity : () -> Unit) = viewModelScope.launch{
        if(emailConfirmationRepository.checkEmailToken(emailToken))
            nextActivity()
    }
}