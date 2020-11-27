package com.toosafinder.restorePassword.restorePassword

import com.toosafinder.api.login.PasswordSetReq
import com.toosafinder.network.HTTPRes
import com.toosafinder.network.convertAnswer

class RestorePasswordRepository(
    private val dataSource: RestorePasswordAPI
) {

    suspend fun registerPassword(emailToken : String, password : String) : HTTPRes<Unit>{
        val res = dataSource.registerPassword(PasswordSetReq(emailToken, password))
        return convertAnswer(res)
    }
}