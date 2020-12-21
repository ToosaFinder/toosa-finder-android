package com.toosafinder.eventInfo

import android.util.Log
import com.toosafinder.api.ApiClient
import com.toosafinder.api.events.GetEventErrors
import com.toosafinder.api.events.GetEventRes
import com.toosafinder.api.get
import com.toosafinder.utils.Option
import java.lang.IllegalArgumentException

class EventInfoRepository (
    private val apiClient: ApiClient
) {

    suspend fun getInfo(id: Int): Option<GetEventRes, GetEventErrors?> =
        apiClient.get<GetEventRes>("event/${id}" )
            .transform(
                onSuccess = {
                    Log.d(EventInfoActivity.logTag, "Data updating... (repo)")
                    Option.success<GetEventRes, GetEventErrors?>(it)
                },
                onConflict = { Option.error(with(it.code) {
                    Log.d(EventInfoActivity.logTag, "Data updating... (repo)")
                    try {
                        GetEventErrors.valueOf(this)
                    } catch (exc: IllegalArgumentException) {
                        null
                    }
                }) }
            ) as Option<GetEventRes, GetEventErrors?>
}