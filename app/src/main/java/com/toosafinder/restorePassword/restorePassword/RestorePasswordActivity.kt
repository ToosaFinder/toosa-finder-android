package com.toosafinder.restorePassword.restorePassword

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Log.ASSERT
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.login.afterTextChanged
import kotlinx.android.synthetic.main.restore_password.*
import org.koin.android.viewmodel.ext.android.getViewModel

class RestorePasswordActivity : AppCompatActivity(){

    private lateinit var restorePasswordViewModel : RestorePasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.restore_password)

        val textFieldPassword = findViewById<EditText>(R.id.textFieldPassword)
        val textFieldRepeatPassword = findViewById<EditText>(R.id.textFieldRepeatPassword)
        val buttonDone = findViewById<Button>(R.id.buttonDone)

        val password = textFieldPassword.text
        val repeatPassword = textFieldRepeatPassword.text

        restorePasswordViewModel = getViewModel()

        //тут надо решить, что делать если ничего не пришло
        val emailToken : String = intent.data?.lastPathSegment ?: throw NullPointerException("Ничего не прислали")

        /*
        * проверки хуерки стейтов
        */

        textFieldPassword.afterTextChanged { password.toString() }

        buttonDone.setOnClickListener { _ ->
            if(restorePasswordViewModel.passwordMatching(password.toString(),repeatPassword.toString()))
                restorePasswordViewModel.registerPassword(emailToken, password.toString())
        }


    }

}