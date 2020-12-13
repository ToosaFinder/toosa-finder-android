package com.toosafinder.MainScreen.MapMainScreen

import android.util.Log
import com.toosafinder.api.*
import com.toosafinder.api.events.GetEventRes
import com.toosafinder.api.events.GetEventsRes
import com.toosafinder.api.login.PasswordRestoreReq
import com.toosafinder.utils.Option
import com.toosafinder.utils.UnitOption

class MapMainScreenRepository(
    private val api: ApiClient
) {
//мб я не прав и это не должно работать
    suspend fun getEvents() : Option<GetEventsRes, ErrorCode?>  =
        api.get<GetEventsRes>("/event", withAuth = true)
            .transform(
                onSuccess = {
                    Log.d("check", "URA POLUCHIL KRUTA")
                    Option.success(it) },
                onConflict = { Option.error(ErrorCode.fromString(it.code)) }
            )
}