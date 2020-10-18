package com.toosafinder.restorePassword.restorePassword

import com.toosafinder.restorePassword.DefaultCallBack
import retrofit2.Call

class RestorePasswordRepository(
    private val dataSource: RestorePasswordDataSource
) {

//    fun restorePassword (email : String) : Unit{
//        val call : Call<Void> = restorePasswordDataSource.restorePassword(email)
//        return call.enqueue(DefaultCallBack<Void>())
//    }

    fun registerPassword(emailToken : String, password : String) {
        val call : Call<Void> =  dataSource.registerPassword(emailToken, password)
        return call.enqueue(DefaultCallBack<Void>())
    }
}