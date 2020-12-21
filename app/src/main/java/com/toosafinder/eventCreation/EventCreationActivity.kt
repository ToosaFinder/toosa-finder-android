package com.toosafinder.eventCreation

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.lujun.androidtagview.TagView.OnTagClickListener
import com.google.android.gms.maps.model.LatLng
import com.toosafinder.MainScreen.MapMainScreen.MapMainScreenActivity
import com.toosafinder.R
import com.toosafinder.login.afterTextChanged
import com.toosafinder.security.SecuredActivity
import com.toosafinder.security.UserSession
import com.toosafinder.utils.ErrorObserver
import kotlinx.android.synthetic.main.content_event_creation.*
import kotlinx.android.synthetic.main.content_event_creation.textErrorMessage
import org.koin.android.ext.android.getKoin
import org.koin.android.viewmodel.ext.android.getViewModel
import java.lang.IllegalStateException
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class EventCreationActivity : SecuredActivity() {
    private val location: MutableLiveData<LatLng> = MutableLiveData()
    private lateinit var eventCreationViewModel: EventCreationViewModel
    private val tags: LiveData<MutableList<String>> = MutableLiveData(MutableList(1) { "" })

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_creation)

        tagView.isEnableCross = true
        tagView.setOnTagClickListener(object : OnTagClickListener {
            override fun onTagClick(position: Int, text: String?) {
                supportFragmentManager.beginTransaction().add(AddTagFragment(eventCreationViewModel, tags, tagView, position), "Add Tag").commit()
            }

            override fun onTagLongClick(position: Int, text: String?) {
                /* no-op */
            }

            override fun onSelectedTagDrag(position: Int, text: String?) {
                /* no-op */
            }

            override fun onTagCrossClick(position: Int) {
                tags.value?.remove(tagView.getTagText(position))
                tagView.removeTag(position)
            }
        })

        eventCreationViewModel = getViewModel()

        buttonFindLocation.setOnClickListener {
            EventCreationLocationMap(location).show(supportFragmentManager, "Location Map")
        }

        buttonAddTag.setOnClickListener {
            supportFragmentManager.beginTransaction().add(AddTagFragment(eventCreationViewModel, tags, tagView), "Add Tag").commit()
        }

        val onDataChanged = {
            eventCreationViewModel.eventDataChange(
                textFieldEventName.text.toString(),
                location.value,
                textFieldEventDate.text.toString(),
                textFieldEventTime.text.toString()
            )
        }

        location.observe(this@EventCreationActivity, {
            textFieldEventLocation.setText("" + it.latitude + ", " + it.longitude)
            onDataChanged()
        })

        val eventCreationErrorObserver = ErrorObserver<EventCreationFormState>(textErrorMessage,
            buttonCreateEvent, EventCreationFormState.Valid, {
            when(it) {
                is EventCreationFormState.EmptyName -> getString(R.string.error_no_name)
                is EventCreationFormState.EmptyLocation -> getString(R.string.error_no_location)
                is EventCreationFormState.InvalidDate -> getString(R.string.error_invalid_date)
                is EventCreationFormState.InvalidTime -> getString(R.string.error_invalid_time)
                is EventCreationFormState.DateOverdue -> getString(R.string.error_date_before)
                else -> getString(R.string.all_valid)
            }
        })

        textFieldEventDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
        textFieldEventTime.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))

        textFieldEventName.afterTextChanged { onDataChanged() }
        textFieldEventDate.afterTextChanged { onDataChanged() }
        textFieldEventTime.afterTextChanged { onDataChanged() }

        //onDataChanged()

        buttonCreateEvent.setOnClickListener {
            progressBarSending.visibility = View.VISIBLE
            buttonCreateEvent.isEnabled = false
            eventCreationViewModel.createEvent(
                textFieldEventName.text.toString(),
                getKoin().get<UserSession>().user()!!.name,
                textFieldEventDescription.text.toString(),
                "",
                location.value!!.latitude.toFloat(),
                location.value!!.longitude.toFloat(),
                seekBarEventSize.progress * 30, // ???
                textFieldEventDate.text.toString(),
                textFieldEventTime.text.toString(),
                switchIsPublic.isChecked,
                tagView.tags
            )
        }

        buttonCancelEvent.setOnClickListener {
            supportFragmentManager.beginTransaction().add(CloseDialog(this), "Close Dialog").commit()
        }

        eventCreationViewModel.eventCreationResult.observe(this@EventCreationActivity) {
            progressBarSending.visibility = View.INVISIBLE
            buttonCreateEvent.isEnabled = true
            it.finalize (
                onSuccess = { finish() },
                onError = {
                    textErrorMessage.visibility = View.VISIBLE
                    textErrorMessage.text = getString(R.string.error_event_creation) + " " + it
                }
            )
        }

        eventCreationViewModel.eventCreationFormState.observe(this@EventCreationActivity, eventCreationErrorObserver)
    }

}