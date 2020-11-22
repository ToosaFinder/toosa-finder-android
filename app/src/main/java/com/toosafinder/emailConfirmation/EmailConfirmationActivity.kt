package com.toosafinder.emailConfirmation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.emailConfirmationModule
import com.toosafinder.login.LoginActivity
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.util.*

class EmailConfirmationActivity : AppCompatActivity() {

    private lateinit var emailConfirmationViewModel : EmailConfirmationViewModel
    private var loginActivityStart : String = "com.toosafinder.login.LoginActivity"

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setContentView(R.layout.email_confirmation)

        loadKoinModules(emailConfirmationModule)
        emailConfirmationViewModel = getViewModel()

        val data: Uri? = intent?.data
        val emailToken: UUID = UUID.fromString(parseData(data)) ?: throw Exception("No uuid was found")
        Log.d("ConfirmationToken",emailToken.toString())

        emailConfirmationViewModel.checkEmailToken(emailToken){
            val intent: Intent = Intent(this@EmailConfirmationActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        unloadKoinModules(emailConfirmationModule)

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