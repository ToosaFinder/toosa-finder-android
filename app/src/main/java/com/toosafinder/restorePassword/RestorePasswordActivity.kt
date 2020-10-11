package com.toosafinder.restorePassword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import kotlinx.android.synthetic.main.restore_password.*
import org.koin.android.viewmodel.ext.android.getViewModel

class RestorePasswordActivity : AppCompatActivity(){

    private lateinit var resorePasswordViewModel : RestorePasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.restore_password)

        val password = textFieldPassword.text
        val repeatPassword = textFieldRepeatPassword.text

        resorePasswordViewModel = getViewModel()

        buttonDone.setOnClickListener { _ ->
            re
        }


    }

}