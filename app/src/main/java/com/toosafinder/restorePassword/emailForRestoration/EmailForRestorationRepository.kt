package com.toosafinder.restorePassword.emailForRestoration

import com.toosafinder.api.login.PasswordRestoreReq
import com.toosafinder.network.HTTPRes
import com.toosafinder.network.convertAnswer

class EmailForRestorationRepository(
    private val dataSource: EmailForRestorationAPI
) {

    suspend fun restorePassword (email : String): HTTPRes<Unit> {
        val res = dataSource.restorePassword(PasswordRestoreReq(email))
        return convertAnswer(res)
    }
}
