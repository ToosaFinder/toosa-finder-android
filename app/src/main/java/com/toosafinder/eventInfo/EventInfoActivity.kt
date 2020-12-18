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

        eventInfoViewModel = getViewModel()
        eventInfoViewModel.receivedData
            .observe(this@EventInfoActivity) { receivedData ->
                Log.d(EventInfoActivity.logTag, "Data treating")
                receivedData.finalize(
                    onSuccess = {
                        updateUI(it)
                        Log.d(EventInfoActivity.logTag,"Successfully update info about event with ID = $eventId")
                    },
                    onError = {
                        Log.d(EventInfoActivity.logTag,"Error during getting info about event with ID = $eventId")
                        showErrorMessage("Ups! Error during loading information about this event.")
                    })
            }

        backButton.setOnClickListener {
            super.finish()
        }

    }

    override fun onStart() {
        super.onStart()

        eventInfoViewModel.getEventInfo(eventId)
        Log.d(logTag, "Function \"onStart()\" event info start displaying")
    }

    private fun updateUI(event: GetEventRes) {
        eventTitle.text = "${event.name} by ${event.creator}"
        eventDescription.text = event.description
        for (tag in event.tags) {
            tagView.addTag(tag)
        }
        eventLocation.text = "Event in ${event.address} \n (${event.latitude}, ${event.longitude})."
        eventTime.text = "From ${event.startTime}"
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}