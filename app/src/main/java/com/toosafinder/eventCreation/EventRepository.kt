package com.toosafinder.eventCreation

import com.toosafinder.api.ApiClient
import com.toosafinder.api.ErrorCode
import com.toosafinder.api.events.EventCreationReq
import com.toosafinder.api.events.EventCreationRes
import com.toosafinder.api.events.PopularTagRes
import com.toosafinder.api.get
import com.toosafinder.api.post
import com.toosafinder.utils.Option

class EventRepository(private val api: ApiClient) {
    suspend fun getPopularTags() : Option<List<String>, ErrorCode?> =
        api.get<PopularTagRes>("/event/tag/popular", null, withAuth = true)
            .transform(
                onSuccess = { Option.success(it.tags) },
                onConflict = { Option.error(ErrorCode.fromString(it.code)) }
            )

    suspend fun createEvent(
        name: String,
        creator: String,
        description: String,
        address: String,
        latitude: Float,
        longitude: Float,
        participantsLimit: Int,
        startTime: java.time.LocalDateTime,
        isPublic: Boolean,
        tags: List<String>) :
            Option<EventCreationRes, ErrorCode?> =
        api.post<EventCreationRes>("/event", EventCreationReq(name, creator, description, address, latitude, longitude, participantsLimit, startTime, isPublic, tags), withAuth = true)
            .transform(
                onSuccess = { Option.success(it) },
                onConflict = { Option.error(ErrorCode.fromString(it.code)) }
            )
}