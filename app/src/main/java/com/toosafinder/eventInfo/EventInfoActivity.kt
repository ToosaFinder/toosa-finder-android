package com.toosafinder.eventInfo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.toosafinder.R
import com.toosafinder.api.events.GetEventRes
import com.toosafinder.security.SecuredActivity
import kotlinx.android.synthetic.main.activity_event_info2.*
import org.koin.android.viewmodel.ext.android.getViewModel
import kotlin.NumberFormatException

/**
 * To switch to this activity you need to add
 * "eventId" = required_event_id to Intent
 */
class EventInfoActivity : SecuredActivity() {
    private lateinit var eventInfoViewModel: EventInfoViewModel
    private var eventId = -1

    private final val TAG: String = "EVENT_INFO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventId = intent.getStringExtra("eventId").let {
            try {
                it.toString().toInt()
            } catch (exc: NullPointerException) {
                showErrorMessage("NullPointerError(неверный вызов активити)")
                Log.e(TAG, "Getting event exception")
            } catch (exc: NumberFormatException) {
                showErrorMessage("ParsingIdError(хм..)")
                Log.e(TAG, "Getting event exception")
            }
        }
        //имхо костыльно, ничего лучше не придумал
//        if (eventId == -1) finish()

        eventInfoViewModel = getViewModel()

        backButton.setOnClickListener {
            super.finish()
        }

        setContentView(R.layout.activity_event_info2)
    }

    override fun onStart() {
        super.onStart()

        val event: GetEventRes? = try {
            eventInfoViewModel.getEventInfo(eventId)
        } catch (exc: NoInfoException) {
            showErrorMessage("An error during obtaining information")
            null
        }
        Log.d(TAG, "Function \"onStart()\" event info start displaying")

        if (event != null) {
            eventTitle.text = "${event.name} by ${event.creator}"
            eventDescription.text = event.description
            for (tag in event.tags) {
                tagView.addTag(tag)
            }
            eventLocation.text = "Event in ${event.address} \n (${event.latitude}, ${event.longitude})."
            eventTime.text = "From ${event.startTime}"

        }
//        eventInfoViewModel.updateEventInfo(eventId)
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}