package com.toosafinder.emailConfirmation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.login.LoginActivity
import java.util.*

class EmailConfirmationActivity : AppCompatActivity() {

    private lateinit var emailConfirmationViewModel : EmailConfirmationViewModel
    private var loginActivityStart : String = "com.toosafinder.login.LoginActivity"

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setContentView(R.layout.email_confirmation)

        //val action: String? = intent?.action

        //Надо придумать как обрабатывать
        val data: Uri? = intent?.data
        val emailToken: UUID = UUID.fromString(parseData(data)) ?: throw NullPointerException()

        emailConfirmationViewModel.checkEmailToken(emailToken) {
            val intent: Intent = Intent(this@EmailConfirmationActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

//     Попытка переделать с Sealed class, но не понял зачем он тут, у нас же просто вытаскивается значение uuid,
//     нет наших никаких наших состояний (мб я просто тупой и не понял)
//    private fun parseData(uuidFromUri: UuidFromUrl): String = when (uuidFromUri) {
//        is UuidFromUrl.ValidUuid -> uuidFromUri.data.lastPathSegment!!
//        is UuidFromUrl.InvalidUuid -> getString(uuidFromUri.error)
//    }
    private fun parseData(data: Uri?): String? {
            return data?.lastPathSegment
    }
}