package com.toosafinder.restorePassword

import com.toosafinder.restorePassword.emailForRestoration.EmailForRestorationView

sealed class SendingResult {
    data class Success(val emailForRestorationView: EmailForRestorationView): SendingResult()
    data class Error(var error: Int): SendingResult()
}