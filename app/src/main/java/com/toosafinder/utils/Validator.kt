package com.toosafinder.utils

import android.util.Patterns

fun isUserNameValid(username: String): Boolean =
    if (username.contains('@')) {
        Patterns.EMAIL_ADDRESS.matcher(username).matches()
    } else {
        username.isNotBlank()
    }

fun isEmailValid(email: String): Boolean =
    email.contains(Regex(".*@.*"))

// A placeholder password validation check
fun isPasswordValid(password: String): Boolean = password.length > 5