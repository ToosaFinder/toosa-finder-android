package com.toosafinder.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R
import com.toosafinder.registration.RegistrationActivity
import com.toosafinder.restorePassword.emailForRestoration.EmailForRestorationActivity
import com.toosafinder.utils.ErrorObserver
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.progressBarSending
import kotlinx.android.synthetic.main.activity_login.textErrorMessage
import kotlinx.android.synthetic.main.content_registration.*
import org.koin.android.viewmodel.ext.android.getViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        loginViewModel = getViewModel()

        val loginErrorObserver = ErrorObserver<LoginFormState>(textErrorMessage, login, LoginFormState.Valid, {
            when(it) {
                is LoginFormState.InvalidLogin -> getString(R.string.error_invalid_username)
                is LoginFormState.InvalidPassword -> getString(R.string.error_invalid_password_short)
                is LoginFormState.Valid -> getString(R.string.all_valid)
            }
        })

        loginViewModel.loginFormState.observe(this@LoginActivity, loginErrorObserver)

        loginViewModel.loginResult.observe(this@LoginActivity) { loginResult ->
            progressBarSending.visibility = View.INVISIBLE
            Log.e("LoginActivity", "login error received4")
            loginResult.finalize(
                    onSuccess = ::updateUiWithUser,
                    onError = {
                        Log.i("LoginActivity", "login error received")
                        textErrorMessage.text = getString(R.string.error_login) + " " + it
                    }
                )
            }

        val onDataChanged = {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        onDataChanged()

        username.afterTextChanged { onDataChanged() }

        password.apply {
            afterTextChanged { onDataChanged() }

            login.setOnClickListener {
                progressBarSending.visibility = View.VISIBLE
                textErrorMessage.text = getString(R.string.all_valid)
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }

        signUpButton.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegistrationActivity::class.java
                )
            )
        }

        forgotPasswordButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, EmailForRestorationActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        Toast.makeText(
                applicationContext,
                "$welcome $displayName",
                Toast.LENGTH_LONG
        ).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}