package com.toosafinder.restorePassword.emailForRestoration

import android.util.Log
import com.toosafinder.api.login.PasswordRestoreReq
import com.toosafinder.network.HTTPRes
import com.toosafinder.network.convertAnswer

class EmailForRestorationRepository(
    private val api: EmailForRestorationAPI
) {

    suspend fun restorePassword (email : String): HTTPRes<Unit>{
        val result = convertAnswer(api.restorePassword(PasswordRestoreReq(email)))
        when(result){
            is HTTPRes.Success -> Log.d("SuccessfulConfirmation", "Success")
            is HTTPRes.Conflict -> Log.d("ConfirmationError", result.code)
        }
        return result
    }
}
