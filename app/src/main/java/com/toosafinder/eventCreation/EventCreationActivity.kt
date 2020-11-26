package com.toosafinder.eventCreation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.lujun.androidtagview.TagView.OnTagClickListener
import com.toosafinder.R
import kotlinx.android.synthetic.main.content_event_creation.*

class EventCreationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_creation)

        tagView.isEnableCross = true
        tagView.setOnTagClickListener(object : OnTagClickListener {
            override fun onTagClick(position: Int, text: String?) {
                TODO("Not yet implemented")
            }

            override fun onTagLongClick(position: Int, text: String?) {
                TODO("Not yet implemented")
            }

            override fun onSelectedTagDrag(position: Int, text: String?) {
                TODO("Not yet implemented")
            }

            override fun onTagCrossClick(position: Int) {
                tagView.removeTag(position)
            }
        })
        tagView.addTag("Bek")
        tagView.addTag("Nek")
        tagView.addTag("Serezha")
        tagView.addTag("Motya")
    }
}