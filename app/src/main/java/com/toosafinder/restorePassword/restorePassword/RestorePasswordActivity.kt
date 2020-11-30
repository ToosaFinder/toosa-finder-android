package com.toosafinder.restorePassword.restorePassword

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.login.LoginActivity
import com.toosafinder.login.afterTextChanged
import kotlinx.android.synthetic.main.restore_password.*
import org.koin.android.viewmodel.ext.android.getViewModel

class RestorePasswordActivity : AppCompatActivity(){

    private lateinit var restorePasswordViewModel : RestorePasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.restore_password)

        restorePasswordViewModel = getViewModel()

        //TODO: Кто обработает это исключение? приложение не упадет?
        val emailToken : String = intent.data?.lastPathSegment ?: throw NullPointerException("Ничего не прислали")

        restorePasswordViewModel.restorePasswordState.observe(this@RestorePasswordActivity){
            textErrorMessage.text = when(it) {
                is RestorePasswordState.UnequalPasswords -> getString(R.string.error_invalid_password_unequal)
                is RestorePasswordState.InvalidPassword -> getString(R.string.error_invalid_password_short)
                is RestorePasswordState.Valid -> getString(R.string.all_valid)
            }
            buttonDone.isEnabled = it is RestorePasswordState.Valid
        }

        restorePasswordViewModel.restorePasswordResult.observe(this@RestorePasswordActivity) { restorePasswordResult ->
            restorePasswordResult.finalize(
                onSuccess = {
                    startActivity(Intent(this@RestorePasswordActivity, LoginActivity::class.java))
                },
                onError = {
                    textErrorMessage.text = getString(R.string.invalid_email_token)
                }
            )
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
            textErrorMessage.text = getString(R.string.all_valid)
            restorePasswordViewModel.registerPassword(emailToken, textFieldPassword.text.toString())
        }
    }

}