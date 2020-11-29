package com.toosafinder.emailConfirmation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.utils.launchWithErrorLogging
import java.util.*


//TODO: обработать ошибку от сервера
// что произойдет в приложении если сервер ответит что токен невалиден?
class EmailConfirmationViewModel(
    private val emailConfirmationRepository: EmailConfirmationRepository
): ViewModel() {

    fun checkEmailToken(emailToken : UUID, nextActivity : () -> Unit) = viewModelScope.launchWithErrorLogging {
        emailConfirmationRepository.checkEmailToken(emailToken)
        nextActivity()
    }
}