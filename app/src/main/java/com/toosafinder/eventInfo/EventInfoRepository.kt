package com.toosafinder.eventInfo

import com.toosafinder.api.ApiClient
import com.toosafinder.api.ErrorCode
import com.toosafinder.api.events.EventDeletionErrors
import com.toosafinder.api.events.EventRes
import com.toosafinder.api.get
import com.toosafinder.security.UserSession
import com.toosafinder.utils.Option
import java.lang.IllegalArgumentException

class EventInfoRepository (
    private val apiClient: ApiClient
) {

    suspend fun getInfo(id: Int): Option<EventRes, EventDeletionErrors?> =
        apiClient.get<EventRes>("event/${id}" )
            .transform(
                onSuccess = { Option.success<EventRes, EventDeletionErrors?>(it) },
                onConflict = { Option.error(with(it.code) {
                    try {
                        EventDeletionErrors.valueOf(this)
                    } catch (exc: IllegalArgumentException) {
                        null
                    }
                }) }
            ) as Option<EventRes, EventDeletionErrors?>
}