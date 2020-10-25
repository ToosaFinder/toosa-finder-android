package com.toosafinder.restorePassword.emailForRestoration

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.login.LoginViewModel
import com.toosafinder.login.afterTextChanged
import kotlinx.android.synthetic.main.email_for_restoration.*
import org.koin.android.viewmodel.ext.android.getViewModel

class EmailForRestorationActivity :  AppCompatActivity(){

    private var nextActivity : String = "com.toosafinder.login.LoginActivity"

    private lateinit var emailForRestorationViewModel: EmailForRestorationViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContentView(R.layout.email_for_restoration)

        val textFieldEmail = findViewById<EditText>(R.id.textFieldEmail)
        val buttonContinue = findViewById<Button>(R.id.buttonContinue)

        val email = textFieldEmail.text

        emailForRestorationViewModel = getViewModel()

        /*
        мб нужно добавить проверки на модел вью какие-то
         */
        //TODO email validation
        textFieldEmail.apply{
            afterTextChanged{ email.toString() }

            buttonContinue.setOnClickListener{ _ ->
                emailForRestorationViewModel.sendEmail(email.toString())
                textFieldEmail.visibility = View.INVISIBLE
                buttonContinue.visibility = View.INVISIBLE
                textAfterClick.visibility = View.VISIBLE
                buttonAfterClick.visibility = View.VISIBLE
            }

            buttonAfterClick.setOnClickListener{
                val intent : Intent = Intent(nextActivity)
                startActivity(intent)
            }
        }



    }
}
