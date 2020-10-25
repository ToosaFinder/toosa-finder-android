package com.toosafinder.restorePassword.emailForRestoration

import com.toosafinder.api.login.PasswordRestoreReq
import com.toosafinder.network.HTTPRes

class EmailForRestorationRepository(
    private val dataSource: EmailForRestorationDataSource
) {

    suspend fun restorePassword (email : String): Unit =
        when(dataSource.restorePassword(PasswordRestoreReq(email))){
            is HTTPRes.Success -> TODO()
            is HTTPRes.Conflict -> TODO()
        }
}
