package com.toosafinder.restorePassword.emailForRestoration

import com.toosafinder.restorePassword.DefaultCallBack
import com.toosafinder.restorePassword.restorePassword.RestorePasswordDataSource
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EmailForRestorationRepository {

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://vk.com/").addConverterFactory(
        GsonConverterFactory.create()).build()
    private val emailForRestorationDataSource: EmailForRestorationDataSource = retrofit.create(
        EmailForRestorationDataSource::class.java)

    suspend fun restorePassword (email : String) : Unit{
        val call : Call<Void> = emailForRestorationDataSource.resetPassword(email)
        return call.enqueue(DefaultCallBack<Void>())
    }
}