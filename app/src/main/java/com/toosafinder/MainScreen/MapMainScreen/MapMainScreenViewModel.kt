package com.toosafinder.MainScreen.MapMainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.api.ErrorCode
import com.toosafinder.api.events.GetEventsRes
import com.toosafinder.utils.Option
import com.toosafinder.utils.launchWithErrorLogging
import com.toosafinder.utils.mapSuccess

class MapMainScreenViewModel(
    private val mapMainScreenRepository: MapMainScreenRepository
) : ViewModel(){

    private val _mapState = MutableLiveData<MapMainScreenState>()
    val mapState: LiveData<MapMainScreenState> = _mapState

    private val _mapResult = MutableLiveData<Option<GetEventsRes, ErrorCode?>>()
    val mapResult: LiveData<Option<GetEventsRes, ErrorCode?>> = _mapResult

    fun getEvents() = viewModelScope.launchWithErrorLogging{
        _mapResult.value = mapMainScreenRepository.getEvents().mapSuccess { GetEventsRes(it.events) }
    }
}