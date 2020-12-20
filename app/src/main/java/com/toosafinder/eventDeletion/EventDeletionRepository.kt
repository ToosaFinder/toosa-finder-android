package com.toosafinder.eventDeletion

import com.toosafinder.api.ApiClient
import com.toosafinder.api.deleteUnit
import com.toosafinder.api.events.EventDeletionErrors
import com.toosafinder.utils.UnitOption
import java.lang.IllegalArgumentException

class EventDeletionRepository(
    private val api: ApiClient
) {
    suspend fun deleteEvent(id: Int): UnitOption<EventDeletionErrors?> {
        return api.deleteUnit("event/${id}")
            .transform(
                onSuccess = { UnitOption.success() },
                onConflict = { UnitOption.error(with(it.code) {
                    try {
                        EventDeletionErrors.valueOf(this)
                    } catch (exc: IllegalArgumentException) {
                        null
                    }
                }) }
            )
    }
}