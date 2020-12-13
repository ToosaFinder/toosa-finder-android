package com.toosafinder.eventInfo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.toosafinder.R
import com.toosafinder.security.SecuredActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.getViewModel
import java.lang.IllegalArgumentException
import kotlin.NumberFormatException

/**
 * To switch to this activity you need to add
 * "eventId" = required_event_id to Intent
 */
class EventInfoActivity : SecuredActivity() {
    private lateinit var eventInfoViewModel: EventInfoViewModel
    private var eventId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventInfoViewModel = getViewModel()
        eventId = intent.getStringExtra("eventId").let {
            try {
                it.toString().toInt()
            } catch (exc: NullPointerException) {
                showErrorMessage("Error")
                Log.e("EVENT_INFO", "Getting event exception")
            } catch (exc: NumberFormatException) {
                showErrorMessage("Error")
                Log.e("EVENT_INFO", "Getting event exception")
            }
        }

//        intent.

        setContentView(R.layout.activity_event_info2)
    }

    override fun onStart() {
        super.onStart()

//        eventInfoViewModel.updateEventInfo(eventId)
    }

    private fun showErrorMessage(message : String) {
        Toast.makeText(
            applicationContext,
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}