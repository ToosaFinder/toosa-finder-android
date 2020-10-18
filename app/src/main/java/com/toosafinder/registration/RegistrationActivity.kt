package com.toosafinder.registration

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.login.afterTextChanged
import kotlinx.android.synthetic.main.content_registration.*
import org.koin.android.viewmodel.ext.android.getViewModel

class RegistrationActivity : AppCompatActivity() {
    private lateinit var registrationViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        registrationViewModel = getViewModel()

        registrationViewModel.registrationFormState.observe(this@RegistrationActivity) { registrationState ->
            when(registrationState) {
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
            buttonContinue.isEnabled = registrationState == RegistrationFormState.Valid
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
            registrationViewModel.registerUser(DTOUser(textFieldEmail.toString(),
                textFieldLogin.toString(), textFieldPassword.toString()))
        }
    }
}