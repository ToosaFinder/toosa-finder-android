package com.toosafinder.restorePassword.restorePassword

import com.toosafinder.api.login.PasswordSetReq
import com.toosafinder.network.HTTPRes

class RestorePasswordRepository(
    private val dataSource: RestorePasswordDataSource
) {

//    fun restorePassword (email : String) : Unit{
//        val call : Call<Void> = restorePasswordDataSource.restorePassword(email)
//        return call.enqueue(DefaultCallBack<Void>())
//    }

    suspend fun registerPassword(emailToken : String, password : String) : HTTPRes<Unit>{
        return dataSource.registerPassword(PasswordSetReq(emailToken, password))
    }
}