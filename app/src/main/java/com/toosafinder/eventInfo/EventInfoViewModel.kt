package com.toosafinder.eventInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.api.events.GetEventRes
import com.toosafinder.utils.launchWithErrorLogging

class EventInfoViewModel (
    private val eventInfoRepository : EventInfoRepository
): ViewModel() {

    private var savedInfo: MutableMap<Int, GetEventRes> = mutableMapOf<Int, GetEventRes>()

    private fun updateEventInfo(id : Int) = viewModelScope.launchWithErrorLogging {
        eventInfoRepository.getInfo(id).finalize(
            //если при выполнении последнего запроса EventNotFound
            //информация о мероприятии удаляется из списка, как
            //неактуальная
            onSuccess = { savedInfo[id] = it },
            onError = { savedInfo.remove(id) }
        )
    }

    fun getEventInfo(id: Int): GetEventRes {
//        if (!savedInfo.containsKey(id)) {
            updateEventInfo(id)
//        }
        return savedInfo[id] ?: throw NoInfoException(id)
    }

//    fun parseServerAnswer(): Unit {}
}