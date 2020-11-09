package com.toosafinder.restorePassword.restorePassword

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.login.LoginActivity
import com.toosafinder.login.afterTextChanged
import com.toosafinder.network.HTTPRes
import kotlinx.android.synthetic.main.restore_password.*
import org.koin.android.viewmodel.ext.android.getViewModel

class RestorePasswordActivity : AppCompatActivity(){

    private lateinit var restorePasswordViewModel : RestorePasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

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
                is HTTPRes.Success -> startActivity(Intent(this@RestorePasswordActivity, LoginActivity::class.java))
            }
        }

        val onDataChange = {
            restorePasswordViewModel.prestorePasswordDataChanged(
                emailToken,
                textFieldPassword.text.toString(),
                textFieldRepeatPassword.text.toString()
            )
        }

        textFieldPassword.afterTextChanged { onDataChange() }


        buttonDone.setOnClickListener {
                restorePasswordViewModel.registerPassword(emailToken, textFieldPassword.text.toString())
        }


    }

}