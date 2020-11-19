package com.toosafinder.restorePassword.restorePassword

import com.toosafinder.api.login.PasswordSetReq
import com.toosafinder.network.HTTPRes

class RestorePasswordRepository(
    private val dataSource: RestorePasswordDataSource
) {


    suspend fun registerPassword(emailToken : String, password : String) : HTTPRes<Unit>{
        return dataSource.registerPassword(PasswordSetReq(emailToken, password))
    }
}