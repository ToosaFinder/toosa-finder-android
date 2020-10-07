package com.toosafinder.emailConfirmation

import android.net.Uri

sealed class UuidFromUrl {
    class ValidUuid (val data : Uri) : UuidFromUrl()
    class InvalidUuid (val error : Int) : UuidFromUrl()
}