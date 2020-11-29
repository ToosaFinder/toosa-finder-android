package com.toosafinder.eventCreation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.lujun.androidtagview.TagView.OnTagClickListener
import com.toosafinder.R
import com.toosafinder.security.SecuredActivity
import kotlinx.android.synthetic.main.content_event_creation.*

class EventCreationActivity : SecuredActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_creation)

        tagView.isEnableCross = true
        //TODO: "TODO" должно содержать осмысленное сообщение
        // если хочется обозначить что метод пустой, напиши "//noop"
        // А еще лучше вынести эту конструкцию в класс, метод или переменную
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