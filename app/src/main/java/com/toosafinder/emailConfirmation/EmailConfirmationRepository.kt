package com.toosafinder.emailConfirmation

import android.util.Log
import com.toosafinder.network.HTTPRes
import com.toosafinder.network.convertAnswer
import java.util.*

class EmailConfirmationRepository(
    private val api: EmailConfirmationAPI) {

    suspend fun checkEmailToken(emailToken : UUID) {
        val result = api.checkEmailToken(emailToken)
        when (val mes: HTTPRes<Unit> = convertAnswer(result)) {
            is HTTPRes.Success -> Log.d("SuccessfulConfirmation", "Success")
            is HTTPRes.Conflict -> Log.d("ConfirmationError", mes.code)
        }
    }


}