package com.toosafinder.eventInfo

import android.util.Log
import androidx.lifecycle.*
import com.toosafinder.api.ErrorCode
import com.toosafinder.api.events.EventDeletionErrors
import com.toosafinder.api.events.GetEventErrors
import com.toosafinder.api.events.GetEventRes
import com.toosafinder.api.events.GetEventsRes
import com.toosafinder.eventDeletion.EventDeletionRepository
import com.toosafinder.login.LoggedInUserView
import com.toosafinder.utils.Option
import com.toosafinder.utils.UnitOption
import com.toosafinder.utils.launchWithErrorLogging
import com.toosafinder.utils.mapSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventInfoViewModel (
    private val eventInfoRepository : EventInfoRepository,
    private val eventDeletionRepository: EventDeletionRepository
): ViewModel() {

//    private var savedInfo: MutableMap<Int, GetEventRes> = mutableMapOf<Int, GetEventRes>()
//    private val _savedInfo = MutableLiveData<MutableMap<Int, GetEventRes>>()
//    val savedInfo: LiveData<MutableMap<Int, GetEventRes>> = _savedInfo

    private val receivedData = MutableLiveData<Option<GetEventRes, GetEventErrors?>>()
//    val receivedData: MutableLiveData<Option<GetEventRes, GetEventErrors?>> = _receivedData
    private val _deletionAnswer = MutableLiveData<UnitOption<EventDeletionErrors?>>()
    val deletionAnswer: LiveData<UnitOption<EventDeletionErrors?>> = _deletionAnswer

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
            var res: Option<GetEventRes, GetEventErrors?>
            withContext(Dispatchers.IO) {
                Log.d(EventInfoActivity.logTag, "ON GET EVENT INFO")
                res = eventInfoRepository.getInfo(id)
                Log.d(EventInfoActivity.logTag, "Data updating... (model)")
            }
            receivedData.value = res
        }

    fun deleteEvent(id: Int) = viewModelScope.launchWithErrorLogging {
        var res: UnitOption<EventDeletionErrors?>
        withContext(Dispatchers.IO) {
            res = eventDeletionRepository.deleteEvent(id)/*.mapSuccess { GetEventsRes(it.events) }*/
        }
        _deletionAnswer.value = res
    }

    fun addInfoObserver(owner: LifecycleOwner, successHandler: (GetEventRes) -> Unit, errorHandler: (GetEventErrors?) -> Unit) {
        receivedData.observe(owner) { data->
            data.finalize(
                onSuccess = successHandler,
                onError = errorHandler
            )
        }
    }

    fun addDeletionObserver(owner: LifecycleOwner, successHandler: () -> Unit, errorHandler: (EventDeletionErrors?) -> Unit) {
        deletionAnswer.observe(owner) { data->
            data.finalize(
                onSuccess = successHandler,
                onError = errorHandler
            )
        }
    }
    fun removeObserver(owner: LifecycleOwner) {
        receivedData.removeObservers(owner)
    }

}