package com.toosafinder.restorePassword.emailForRestoration

import com.toosafinder.api.login.PasswordRestoreReq
import com.toosafinder.network.HTTPRes
import com.toosafinder.network.convertAnswer

class EmailForRestorationRepository(
    private val api: EmailForRestorationAPI
) {

    suspend fun restorePassword (email : String): HTTPRes<Unit>{
        return convertAnswer(api.restorePassword(PasswordRestoreReq(email)))
    }
}
