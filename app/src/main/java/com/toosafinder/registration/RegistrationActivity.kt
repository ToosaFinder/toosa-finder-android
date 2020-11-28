package com.toosafinder.registration

import android.content.Intent
import android.graphics.Paint
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.login.LoginActivity
import com.toosafinder.login.afterTextChanged
import com.toosafinder.network.HTTPRes
import com.toosafinder.registrationModule
import com.toosafinder.utils.ErrorObserver
import kotlinx.android.synthetic.main.content_registration.*
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class RegistrationActivity : AppCompatActivity() {
    private lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        loadKoinModules(registrationModule)

        registrationViewModel = getViewModel()

        val registrationErrorObserver = ErrorObserver<RegistrationFormState>(textErrorMessage, buttonContinue,
            RegistrationFormState.Valid, {
            when (it) {
                is RegistrationFormState.InvalidEmail -> getString(R.string.error_invalid_email)
                is RegistrationFormState.UnequalPasswords -> getString(R.string.error_invalid_password_unequal)
                is RegistrationFormState.InvalidPassword -> getString(R.string.error_invalid_password_short)
                is RegistrationFormState.NoAgreement -> getString(R.string.error_no_agreement)
                is RegistrationFormState.Valid -> getString(R.string.all_valid)
            }
        })

        registrationViewModel.registrationFormState.observe(this@RegistrationActivity, registrationErrorObserver)

        registrationViewModel.registrationResult.observe(this@RegistrationActivity) {
            progressBarSending.visibility = View.INVISIBLE
            when (it) {
                is HTTPRes.Success -> startLoginActivity()
                is HTTPRes.Conflict -> textErrorMessage.text =
                    getString(R.string.error_registration) + " " + it.message
            }
        }

        val onDataChanged = {
            registrationViewModel.registrationDataChange(
                textFieldEmail.text.toString(),
                textFieldPassword.text.toString(),
                textFieldPasswordConfirmation.text.toString(),
                checkBoxAgree.isChecked
            )
        }

        onDataChanged()

        promptAccountQuestion.setOnClickListener { startLoginActivity() }
        promptAccountQuestion.paintFlags = promptAccountQuestion.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        textFieldEmail.afterTextChanged { onDataChanged() }
        textFieldLogin.afterTextChanged { onDataChanged() }
        textFieldPassword.afterTextChanged { onDataChanged() }
        textFieldPasswordConfirmation.afterTextChanged { onDataChanged() }
        checkBoxAgree.setOnClickListener { onDataChanged() }

        buttonContinue.setOnClickListener {
            progressBarSending.visibility = View.VISIBLE
            textErrorMessage.text = getString(R.string.all_valid)
            registrationViewModel.registerUser(
                textFieldEmail.text.toString(),
                textFieldLogin.text.toString(), textFieldPassword.text.toString()
            )
        }

        unloadKoinModules(registrationModule)
    }

    private fun startLoginActivity() {
        startActivity(
            Intent(
                this@RegistrationActivity,
                LoginActivity::class.java
            )
        )
    }
}