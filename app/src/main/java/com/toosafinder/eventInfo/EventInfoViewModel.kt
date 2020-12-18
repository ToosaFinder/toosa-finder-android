package com.toosafinder.eventInfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.api.ErrorCode
import com.toosafinder.api.events.GetEventErrors
import com.toosafinder.api.events.GetEventRes
import com.toosafinder.login.LoggedInUserView
import com.toosafinder.utils.Option
import com.toosafinder.utils.launchWithErrorLogging
import com.toosafinder.utils.mapSuccess

class EventInfoViewModel (
    private val eventInfoRepository : EventInfoRepository
): ViewModel() {

//    private var savedInfo: MutableMap<Int, GetEventRes> = mutableMapOf<Int, GetEventRes>()
//    private val _savedInfo = MutableLiveData<MutableMap<Int, GetEventRes>>()
//    val savedInfo: LiveData<MutableMap<Int, GetEventRes>> = _savedInfo

    private val _receivedData = MutableLiveData<Option<GetEventRes, GetEventErrors?>>()
    val receivedData: LiveData<Option<GetEventRes, GetEventErrors?>> = _receivedData

    fun getEventInfo(id : Int) = viewModelScope.launchWithErrorLogging {
//        eventInfoRepository.getInfo(id).finalize(
//            //если при выполнении последнего запроса EventNotFound
//            //информация о мероприятии удаляется из списка, как
//            //неактуальная
//            onSuccess = {
//
//                savedInfo.put(id, it)
//                Log.d(EventInfoActivity.logTag, "Success request to $id")
//            },
//            onError = {
//                savedInfo.remove(id)
//                Log.d(EventInfoActivity.logTag, "Bad request to ID $id")
//            }
//        )
        _receivedData.value = eventInfoRepository.getInfo(id)
    }

//    fun getEventInfo(id: Int): GetEventRes {
//        updateEventInfo(id)
//        val retVal: GetEventRes? = savedInfo[id]
//        Log.d(EventInfoActivity.logTag, "Event got if \"getEventInfo()\" function $retVal, ID = $id")
//        Log.d(EventInfoActivity.logTag, savedInfo.toString())

//        return savedInfo[id] ?: throw NoInfoException(id)
//    }

//    fun parseServerAnswer(): Unit {}
}