package com.toosafinder.eventCreation

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.lujun.androidtagview.TagView.OnTagClickListener
import com.google.android.gms.maps.model.LatLng
import com.toosafinder.R
import com.toosafinder.security.SecuredActivity
import kotlinx.android.synthetic.main.content_event_creation.*
import org.koin.android.viewmodel.ext.android.getViewModel

class EventCreationActivity : SecuredActivity() {
    private val location: MutableLiveData<LatLng> = MutableLiveData()
    private lateinit var eventCreationViewModel: EventCreationViewModel
    private val tags: LiveData<MutableList<String>> = MutableLiveData(MutableList(1) { "" })

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
    }
}