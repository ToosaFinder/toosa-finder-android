package com.toosafinder.restorePassword.restorePassword

import com.toosafinder.restorePassword.DefaultCallBack
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestorePasswordRepository {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://vk.com/").addConverterFactory(
        GsonConverterFactory.create()).build()
    private val restorePasswordDataSource: RestorePasswordDataSource = retrofit.create(
        RestorePasswordDataSource::class.java)

//    fun restorePassword (email : String) : Unit{
//        val call : Call<Void> = restorePasswordDataSource.restorePassword(email)
//        return call.enqueue(DefaultCallBack<Void>())
//    }

    fun registerPassword(emailToken : String, password : String) : Unit {
        val call : Call<Void> =  restorePasswordDataSource.registerPassword(emailToken, password)
        return call.enqueue(DefaultCallBack<Void>())
    }
}