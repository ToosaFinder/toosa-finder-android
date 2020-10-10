package com.toosafinder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.registration.RegistrationRepository
import kotlinx.android.synthetic.main.content_registration.*

class RegistrationActivity : AppCompatActivity() {
    private val registrationRepository: RegistrationRepository = RegistrationRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        buttonContinue.setOnClickListener() { _ -> processRegistration() }
    }

    private fun processRegistration() {
        Log.d("HUy", "serezha")
        registrationRepository.registerUser(textFieldEmail.text.toString(),
            textFieldLogin.text.toString(), textFieldPassword.text.toString())
    }
}