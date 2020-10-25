package com.toosafinder.restorePassword

sealed class FormState {
    object Valid : FormState()
    class Invalid(val error: Int) : FormState()
}