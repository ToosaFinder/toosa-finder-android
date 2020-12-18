package com.toosafinder.eventCreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toosafinder.api.ErrorCode
import com.toosafinder.api.events.EventCreationRes
import com.toosafinder.utils.Option
import com.toosafinder.utils.launchWithErrorLogging

class EventCreationViewModel(private val eventRepository: EventRepository) : ViewModel() {
    private val _eventCreationFormState = MutableLiveData<EventCreationFormState>()
    val eventCreationFormState: LiveData<EventCreationFormState> = _eventCreationFormState

    private val _eventCreationResult = MutableLiveData<Option<EventCreationRes, ErrorCode?>>()
    val eventCreationResult: LiveData<Option<EventCreationRes, ErrorCode?>> = _eventCreationResult

    private val _getTagsResult = MutableLiveData<Option<List<String>, ErrorCode?>>()
    val getTagsResult: LiveData<Option<List<String>, ErrorCode?>> = _getTagsResult

    fun getPopularTags() = viewModelScope.launchWithErrorLogging {
        _getTagsResult.value = eventRepository.getPopularTags()
    }

    fun createEvent() {

    }
}