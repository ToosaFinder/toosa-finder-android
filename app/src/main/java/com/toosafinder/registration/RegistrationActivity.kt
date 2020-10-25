package com.toosafinder.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.login.LoginActivity
import com.toosafinder.login.afterTextChanged
import com.toosafinder.network.HTTPRes
import com.toosafinder.registrationModule
import kotlinx.android.synthetic.main.content_registration.*
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.context.loadKoinModules

class RegistrationActivity : AppCompatActivity() {
    private lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        loadKoinModules(registrationModule)

        registrationViewModel = getViewModel()

        registrationViewModel.registrationFormState.observe(this@RegistrationActivity) {
            when(it) {
                is RegistrationFormState.InvalidEmail ->
                    textErrorMessage.text = getString(R.string.invalid_email)
                is RegistrationFormState.InvalidLogin ->
                    textErrorMessage.text = getString(R.string.invalid_username)
                is RegistrationFormState.UnequalPasswords ->
                    textErrorMessage.text = getString(R.string.invalid_password_unequal)
                is RegistrationFormState.InvalidPassword ->
                    textErrorMessage.text = getString(R.string.invalid_password_short)
                is RegistrationFormState.Valid ->
                    textErrorMessage.text = getString(R.string.all_valid)
            }
            buttonContinue.isEnabled = it == RegistrationFormState.Valid
        }

        registrationViewModel.registrationResult.observe(this@RegistrationActivity) {
            progressBarSending.visibility = View.INVISIBLE
            when(it) {
                is HTTPRes.Success -> startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
                is HTTPRes.Conflict -> textErrorMessage.text = getString(R.string.error_registration)
            }
        }

        val onDataChanged = {
            registrationViewModel.registrationDataChange(
                textFieldEmail.text.toString(),
                textFieldLogin.text.toString(),
                textFieldPassword.text.toString(),
                textFieldPasswordConfirmation.text.toString()
            )
        }

        onDataChanged()

        textFieldEmail.afterTextChanged { onDataChanged() }
        textFieldLogin.afterTextChanged { onDataChanged() }
        textFieldPassword.afterTextChanged { onDataChanged() }
        textFieldPasswordConfirmation.afterTextChanged { onDataChanged() }

        buttonContinue.setOnClickListener{
            progressBarSending.visibility = View.VISIBLE
            registrationViewModel.registerUser(textFieldEmail.toString(),
                textFieldLogin.toString(), textFieldPassword.toString())
        }
    }
}