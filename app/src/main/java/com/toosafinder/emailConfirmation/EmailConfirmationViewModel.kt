package com.toosafinder.emailConfirmation

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*

class EmailConfirmationViewModel (private val emailConfirmationRepository: EmailConfirmationRepository) : ViewModel() {


    fun checkEmailToken(emailToken : UUID, nextActivity : () -> Unit) = viewModelScope.launch{
        emailConfirmationRepository.checkEmailToken(emailToken)
        nextActivity()

    }
}