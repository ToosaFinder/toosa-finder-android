package com.toosafinder.eventInfo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.toosafinder.R
import com.toosafinder.api.events.GetEventRes
import com.toosafinder.security.SecuredActivity
import org.koin.android.viewmodel.ext.android.getViewModel
import kotlinx.android.synthetic.main.activity_event_info.*
import kotlinx.android.synthetic.main.content_event_info.*
import org.koin.android.ext.koin.androidContext

/**
 * To switch to this activity you need to add
 * "eventId" = required_event_id to Intent
 */
class EventInfoActivity : SecuredActivity() {
    private lateinit var eventInfoViewModel: EventInfoViewModel
    private var eventId = -1
//    private val tag: String = "EVENT_INFO"
    companion object {
        const val eventIdIntentTag: String = "eventId"
        const val logTag: String = "EVENT_INFO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_info)
        Log.d(EventInfoActivity.logTag, "onCreate")

        eventInfoViewModel = getViewModel()
        eventInfoViewModel.addInfoObserver(
            this@EventInfoActivity,
            ::updateUI,
            {
                Log.d(EventInfoActivity.logTag,"Error during getting info about event with ID = $eventId")
                showMessage("Ups! Error during loading information about this event.")
            })

        eventInfoViewModel.addDeletionObserver(
            this@EventInfoActivity,
            {
                showMessage("Event was successfully deleted!")
                finish()
            },
            {
                showMessage(it.toString())
            }
        )

        buttonOk.setOnClickListener {
            finish()
        }

        buttonDelete.setOnClickListener {
            eventInfoViewModel.deleteEvent(eventId)
        }

        start()
    }

    private fun start() {
        //super.onStart()
        Log.d(EventInfoActivity.logTag, "onStart")

        eventId = intent.getStringExtra(eventIdIntentTag).let {
            try {
                it.toString().toInt()
            } catch (exc: NullPointerException) {
                showMessage("NullPointerError(неверный вызов активити)")
                Log.e(logTag, "Getting event exception")
            } catch (exc: NumberFormatException) {
                showMessage("ParsingIdError(хм..)")
                Log.e(logTag, "Getting event exception")
            }
        }
        if (eventId == -1) finish()

        Log.d(EventInfoActivity.logTag, "onStart(2) $eventId")
        eventInfoViewModel.getEventInfo(eventId)
    }

    private fun updateUI(event: GetEventRes) {
        textFieldEventName.text = event.name
        textFieldEventCreator.text = event.creator
        textFieldEventDescription.text = event.description
        for (tag in event.tags) {
            tagView.addTag(tag)
        }
        textFieldEventSize.text = "Max limit ${event.participantsLimit}"
        textViewEventLocation.text = "Event in ${event.address} \n (${event.latitude}, ${event.longitude})."
        textFieldEventSize.text = "Max participants: ${event.participantsLimit}"
        TextViewTime.text = event.startTime.toString()
//        textViewDate.text = event.startTime.format() toString()

        Log.d(EventInfoActivity.logTag,"Info about event with ID = $eventId was successfully updated")
    }

    private fun showMessage(message: String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}