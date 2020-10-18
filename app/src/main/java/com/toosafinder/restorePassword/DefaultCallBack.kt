package com.toosafinder.restorePassword

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//Этот класс мб надо вынести в data, мб будет юзаться другими пакетами еще

open class DefaultCallBack<T> : Callback<T> {

    override fun onResponse(call : Call<T>, response : Response<T>){

    }


    override fun onFailure(call : Call<T>, t: Throwable ){

    }
}