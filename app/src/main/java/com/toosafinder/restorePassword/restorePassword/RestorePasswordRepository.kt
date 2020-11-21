package com.toosafinder.restorePassword.restorePassword

import android.util.Log
import com.toosafinder.api.login.PasswordRestoreReq
import com.toosafinder.api.login.PasswordSetReq
import com.toosafinder.network.HTTPRes
import com.toosafinder.network.convertAnswer
import java.util.*

class RestorePasswordRepository(
    private val api: RestorePasswordAPI
) {


    suspend fun registerPassword(emailToken : String, password : String) : HTTPRes<Unit>{
        val result =  convertAnswer(api.registerPassword(PasswordSetReq(emailToken, password)))
        when(result){
            is HTTPRes.Success -> Log.d("SuccessfulRegistry", "Success")
            is HTTPRes.Conflict -> Log.d("RegistryError", result.code+" "+result.message)
        }
        return result
    }
}