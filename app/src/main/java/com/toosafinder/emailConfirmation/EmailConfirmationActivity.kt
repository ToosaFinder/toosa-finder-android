package com.toosafinder.emailConfirmation

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R

class EmailConfirmationActivity : AppCompatActivity() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //val action: String? = intent?.action

        //Надо придумать как обрабатывать
        val data: Uri = intent?.data!!
        val emailToken: String = parseData(data)

        intent.putExtra("emailToken" , emailToken)

    }

//     Попытка переделать с Sealed class, но не понял зачем он тут, у нас же просто вытаскивается значение uuid,
//     нет наших никаких наших состояний (мб я просто тупой и не понял)
//    private fun parseData(uuidFromUri: UuidFromUrl): String = when (uuidFromUri) {
//        is UuidFromUrl.ValidUuid -> uuidFromUri.data.lastPathSegment!!
//        is UuidFromUrl.InvalidUuid -> getString(uuidFromUri.error)
//    }
    private fun parseData(data: Uri): String {
            return data.lastPathSegment!!
    }
}