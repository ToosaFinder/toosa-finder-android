package com.toosafinder.MainScreen.MapMainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.toosafinder.api.ErrorCode
import com.toosafinder.api.events.GetEventsRes
import com.toosafinder.utils.Option
import com.toosafinder.utils.launchWithErrorLogging
import com.toosafinder.utils.mapSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MapMainScreenViewModel(
    private val mapMainScreenRepository: MapMainScreenRepository
) : ViewModel(){

    private val _mapState = MutableLiveData<MapMainScreenState>()
    val mapState: LiveData<MapMainScreenState> = _mapState

    private val _mapResult = MutableLiveData<Option<GetEventsRes, ErrorCode?>>()
    val mapResult: LiveData<Option<GetEventsRes, ErrorCode?>> = _mapResult


    fun getEvents() = viewModelScope.launchWithErrorLogging{
        var res: Option<GetEventsRes, ErrorCode?>
        withContext(Dispatchers.IO) {
             res = mapMainScreenRepository.getEvents()/*.mapSuccess { GetEventsRes(it.events) }*/
        }
        _mapResult.value = res
    }
}