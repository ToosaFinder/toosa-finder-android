package com.toosafinder.emailConfirmation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.login.LoginActivity
import org.koin.android.viewmodel.ext.android.getViewModel
import java.util.*

class EmailConfirmationActivity : AppCompatActivity() {

    private lateinit var emailConfirmationViewModel : EmailConfirmationViewModel
    private var loginActivityStart : String = "com.toosafinder.login.LoginActivity"

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setContentView(R.layout.email_confirmation)

        emailConfirmationViewModel = getViewModel()

        //TODO: Переменным не обязательно указывать тип. Котлин сам выведет. Гораздо читабельнее будет,
        // если написать "val uri = intent?.data"
        val data: Uri? = intent?.data

        val emailToken: UUID = UUID.fromString(parseData(data)) ?: UUID.fromString("None token was received")
        Log.d("ConfirmationToken", emailToken.toString())

        emailConfirmationViewModel.emailConfirmationResult.observe(this@EmailConfirmationActivity){
            it.finalize(
                onSuccess = {
                    val intent = Intent(this@EmailConfirmationActivity, LoginActivity::class.java)
                    intent.putExtra("RegistryConfirmation", "Success")
                    startActivity(Intent(this@EmailConfirmationActivity, LoginActivity::class.java))
                },
                onError = {
                    val intent = Intent(this@EmailConfirmationActivity, LoginActivity::class.java)
                    intent.putExtra("RegistryConfirmation", "Error")
                    startActivity(intent)
                }
            )
        }
        emailConfirmationViewModel.checkEmailToken(emailToken)
    }

    private fun parseData(data: Uri?): String? {
            return data?.lastPathSegment
    }
}