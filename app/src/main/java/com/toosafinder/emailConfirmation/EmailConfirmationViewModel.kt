package com.toosafinder.emailConfirmation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.api.ErrorCode
import com.toosafinder.utils.UnitOption
import com.toosafinder.utils.launchWithErrorLogging
import java.util.*


//TODO: обработать ошибку от сервера
// что произойдет в приложении если сервер ответит что токен невалиден?
class EmailConfirmationViewModel(
    private val emailConfirmationRepository: EmailConfirmationRepository
): ViewModel() {

    private val _emailConfirmationResult = MutableLiveData<UnitOption<ErrorCode?>>()
    val emailConfirmationResult: LiveData<UnitOption<ErrorCode?>> = _emailConfirmationResult


    fun checkEmailToken(emailToken : UUID) = viewModelScope.launchWithErrorLogging {
        _emailConfirmationResult.value = emailConfirmationRepository.checkEmailToken(emailToken)
    }
}