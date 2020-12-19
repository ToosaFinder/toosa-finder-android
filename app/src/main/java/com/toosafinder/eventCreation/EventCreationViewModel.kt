package com.toosafinder.eventCreation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.toosafinder.api.ErrorCode
import com.toosafinder.api.events.EventCreationRes
import com.toosafinder.utils.Option
import com.toosafinder.utils.launchWithErrorLogging
import java.text.DateFormat
import java.text.DateFormat.getDateInstance
import java.text.DateFormat.getTimeInstance
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun createEvent(
        name: String,
        creator: String,
        description: String,
        address: String,
        latitude: Float,
        longitude: Float,
        participantsLimit: Int,
        date: String,
        time: String,
        isPublic: Boolean,
        tags: List<String>
    ) = viewModelScope.launchWithErrorLogging {
        _eventCreationResult.value = eventRepository.createEvent(name, creator, description, address,
            latitude, longitude, participantsLimit, getLocalDateTime(date, time)!!, isPublic, tags)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun eventDataChange(name: String, loc: LatLng?, date: String, time: String) {
        _eventCreationFormState.value = when {
            name == "" -> EventCreationFormState.EmptyName
            loc == null -> EventCreationFormState.EmptyLocation
            !checkDate(date) -> EventCreationFormState.InvalidDate
            !checkTime(time) -> EventCreationFormState.InvalidTime
            !checkDateDue(date, time) -> EventCreationFormState.DateOverdue
            else -> EventCreationFormState.Valid
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkDate(date: String) : Boolean {
        return getDate(date) != null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkDateDue(date: String, time: String) : Boolean {
        return when(val d = getLocalDateTime(date, time)) {
            is LocalDateTime -> d.isAfter(LocalDateTime.now())
            else -> false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkTime(time: String) : Boolean {
        //val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return try {
            return LocalTime.parse(time) != null
        } catch(e: Exception) {
            false
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate(date: String) : LocalDate? {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        return try {
            return LocalDate.parse(date, formatter)
        } catch(e: Exception) {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getLocalDateTime(date: String, time: String) : LocalDateTime? {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        return try {
            return LocalDateTime.parse("$date $time", formatter)
        } catch(e: Exception) {
            null
        }
    }
}