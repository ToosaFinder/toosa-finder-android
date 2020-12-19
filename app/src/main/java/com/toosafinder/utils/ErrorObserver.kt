package com.toosafinder.utils

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer

class ErrorObserver<T> (
    private val textErrorMessage: TextView,
    private val buttonContinue: Button?,
    private val valid: T? = null,
    private val getErrorMessage: (T) -> String?,
    private val getVisibility: (T) -> Int = { if(it == valid) View.GONE else View.VISIBLE },
    private val getEnabled: (T) -> Boolean = { it == valid }
) : Observer<T> {

    override fun onChanged(t: T) {
        textErrorMessage.text = getErrorMessage(t)
        textErrorMessage.visibility = getVisibility(t)
        buttonContinue?.isEnabled = getEnabled(t)
    }
}


