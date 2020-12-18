package com.toosafinder.eventInfo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.toosafinder.R
import com.toosafinder.api.events.GetEventRes
import com.toosafinder.security.SecuredActivity
import org.koin.android.viewmodel.ext.android.getViewModel
import kotlinx.android.synthetic.main.activity_event_info.*

/**
 * To switch to this activity you need to add
 * "eventId" = required_event_id to Intent
 */
class EventInfoActivity : SecuredActivity() {
    private lateinit var eventInfoViewModel: EventInfoViewModel
    private var eventId = -1
//    private val tag: String = "EVENT_INFO"
    public companion object {
        const val eventIdIntentTag: String = "eventId"
        const val logTag: String = "EVENT_INFO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_info)
        Log.d(EventInfoActivity.logTag, "onCreate")

        eventInfoViewModel = getViewModel()
        eventInfoViewModel.addObserver(
            this@EventInfoActivity,
            ::updateUI,
                {
                    Log.d(EventInfoActivity.logTag,"Error during getting info about event with ID = $eventId")
                    showErrorMessage("Ups! Error during loading information about this event.")
                })

        backButton.setOnClickListener {
            super.finish()
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(EventInfoActivity.logTag, "onStart")

        eventId = intent.getStringExtra(eventIdIntentTag).let {
            try {
                it.toString().toInt()
            } catch (exc: NullPointerException) {
                showErrorMessage("NullPointerError(неверный вызов активити)")
                Log.e(logTag, "Getting event exception")
            } catch (exc: NumberFormatException) {
                showErrorMessage("ParsingIdError(хм..)")
                Log.e(logTag, "Getting event exception")
            }
        }
        if (eventId == -1) finish()

        Log.d(EventInfoActivity.logTag, "onStart(2)")
        eventInfoViewModel.getEventInfo(eventId)
    }

    private fun updateUI(event: GetEventRes) {
        eventTitle.text = "${event.name} by ${event.creator}"
        eventDescription.text = event.description
        for (tag in event.tags) {
            tagView.addTag(tag)
        }
        eventLocation.text = "Event in ${event.address} \n (${event.latitude}, ${event.longitude})."
        eventTime.text = "From ${event.startTime}"

        Log.d(EventInfoActivity.logTag,"Info about event with ID = $eventId was successfully updated")
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}