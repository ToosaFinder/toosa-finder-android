package com.toosafinder.eventInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.api.events.EventRes
import com.toosafinder.utils.launchWithErrorLogging

class EventInfoViewModel (
    private val eventInfoRepository : EventInfoRepository
): ViewModel() {

//    private var savedInfo: MutableMap<Int, EventRes> = mutableMapOf<Int, EventRes>()

    fun updateEventInfo(id : Int, nextAction : () -> Unit) = viewModelScope.launchWithErrorLogging {
        eventInfoRepository.getInfo(id)
    //        if (savedInfo.containsKey(id)) {
//
//        } else {
//            savedInfo.put(when(eventInfoRepository.getInfo(id)) {
//                is  ->
//            }
//        }
    }

//    fun parseServerAnswer(): Unit {}
}