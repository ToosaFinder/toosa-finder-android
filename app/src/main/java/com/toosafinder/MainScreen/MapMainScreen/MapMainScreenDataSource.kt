package com.toosafinder.MainScreen.MapMainScreen

import com.toosafinder.network.HTTPRes
import retrofit2.http.GET


private const val registerURL : String = "/event"

interface MapScreenDataSource {

    //TODO : возвращаемое значение из библиотеки от бека
    @GET(registerURL)
    suspend fun getEvents() : HTTPRes<Unit>
}