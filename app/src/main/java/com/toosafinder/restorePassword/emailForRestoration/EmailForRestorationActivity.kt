package com.toosafinder.restorePassword.emailForRestoration

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.login.LoginActivity
import com.toosafinder.login.afterTextChanged
import kotlinx.android.synthetic.main.email_for_restoration.buttonContinue
import kotlinx.android.synthetic.main.email_for_restoration.textErrorMessage
import kotlinx.android.synthetic.main.email_for_restoration.textFieldEmail
import kotlinx.android.synthetic.main.email_for_restoration.*
import org.koin.android.viewmodel.ext.android.getViewModel

class EmailForRestorationActivity :  AppCompatActivity(){

    private var nextActivity : String = "com.toosafinder.login.LoginActivity"

    private lateinit var emailForRestorationViewModel: EmailForRestorationViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.email_for_restoration)

        emailForRestorationViewModel = getViewModel()

        emailForRestorationViewModel.emailCForRestorationState.observe(this@EmailForRestorationActivity){
            when(it){
                is EmailForRestorationState.InvalidEmail -> textErrorMessage.text = getString(R.string.error_invalid_email)
                is EmailForRestorationState.Valid -> textErrorMessage.text = getString(R.string.all_valid)
            }
            buttonContinue.isEnabled = it is EmailForRestorationState.Valid
        }

        emailForRestorationViewModel.emailForRestorationResult.observe(this@EmailForRestorationActivity){
            it.finalize(
                onSuccess = {
                    textFieldEmail.visibility = View.GONE
                    buttonContinue.visibility = View.GONE
                    textAfterClick.visibility = View.VISIBLE
                    buttonAfterClick.visibility = View.VISIBLE
                },
                onError = {
                    textErrorMessage.text = getString(R.string.email_not_found)
                }
            )
        }

        val onDataChanged = {
            emailForRestorationViewModel.emailDataChanged(textFieldEmail.text.toString())
        }

        onDataChanged()

        textFieldEmail.afterTextChanged { onDataChanged() }

        buttonContinue.setOnClickListener{ _ ->
            textErrorMessage.text = getString(R.string.all_valid)
            emailForRestorationViewModel.sendEmail(textFieldEmail.text.toString())
        }

        buttonAfterClick.setOnClickListener{
            startActivity(Intent(this@EmailForRestorationActivity, LoginActivity::class.java))
        }
    }
}
