package com.toosafinder.restorePassword.emailForRestoration

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.emailForRestorationModule
import com.toosafinder.login.LoginActivity
import com.toosafinder.login.afterTextChanged
import com.toosafinder.network.HTTPRes
import kotlinx.android.synthetic.main.content_registration.*
import kotlinx.android.synthetic.main.email_for_restoration.*
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class EmailForRestorationActivity :  AppCompatActivity(){

    private var nextActivity : String = "com.toosafinder.login.LoginActivity"

    private lateinit var emailForRestorationViewModel: EmailForRestorationViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.email_for_restoration)

        loadKoinModules(emailForRestorationModule)

        emailForRestorationViewModel = getViewModel()



        emailForRestorationViewModel.emailConfirmationState.observe(this@EmailForRestorationActivity){
            when(it){
                is EmailConfirmationState.InvalidEmail -> textErrorMessage.text = getString(R.string.error_invalid_email)
                is EmailConfirmationState.Valid -> textErrorMessage.text = getString(R.string.all_valid)
            }
            buttonContinue.isEnabled = it is EmailConfirmationState.Valid
        }

        emailForRestorationViewModel.emailConfirmationResult.observe(this@EmailForRestorationActivity){
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
            unloadKoinModules(emailForRestorationModule)
            startActivity(Intent(this@EmailForRestorationActivity, LoginActivity::class.java))
        }



    }
}
