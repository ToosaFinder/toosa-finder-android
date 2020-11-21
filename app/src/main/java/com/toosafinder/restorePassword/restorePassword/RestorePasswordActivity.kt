package com.toosafinder.restorePassword.restorePassword

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.emailForRestorationModule
import com.toosafinder.login.LoginActivity
import com.toosafinder.login.afterTextChanged
import com.toosafinder.network.HTTPRes
import com.toosafinder.restorePasswordModule
import kotlinx.android.synthetic.main.restore_password.*
import org.koin.android.viewmodel.ext.android.getViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class RestorePasswordActivity : AppCompatActivity(){

    private lateinit var restorePasswordViewModel : RestorePasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        loadKoinModules(restorePasswordModule)

        setContentView(R.layout.restore_password)

        restorePasswordViewModel = getViewModel()

        val emailToken : String = intent.data?.lastPathSegment ?: throw NullPointerException("Ничего не прислали")

        restorePasswordViewModel.restorePasswordState.observe(this@RestorePasswordActivity){
            when(it){
                is RestorePasswordState.UnequalPasswords ->
                    textErrorMessage.text = getString(R.string.invalid_password_unequal)
                is RestorePasswordState.InvalidPassword ->
                    textErrorMessage.text = getString(R.string.invalid_password_short)
                is RestorePasswordState.Valid ->
                    textErrorMessage.text = getString(R.string.all_valid)
            }
        }

        restorePasswordViewModel.restorePasswordResult.observe(this@RestorePasswordActivity) {
            when (it) {
                is HTTPRes.Conflict -> textErrorMessage.text = getString(R.string.invalid_email_token)
                is HTTPRes.Success -> {
                    unloadKoinModules(restorePasswordModule)
                    startActivity(Intent(this@RestorePasswordActivity, LoginActivity::class.java))
                }
            }
        }

        val onDataChange = {
            Log.d(textFieldPassword.text.toString(),textFieldRepeatPassword.text.toString())
            restorePasswordViewModel.restorePasswordDataChanged(
                textFieldPassword.text.toString(),
                textFieldRepeatPassword.text.toString()
            )
        }

        onDataChange()

        textFieldPassword.afterTextChanged { onDataChange() }
        textFieldRepeatPassword.afterTextChanged { onDataChange() }

        buttonDone.setOnClickListener {
            restorePasswordViewModel.registerPassword(emailToken, textFieldPassword.text.toString())
        }


    }

}