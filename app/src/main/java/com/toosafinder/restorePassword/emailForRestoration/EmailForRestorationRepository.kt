package com.toosafinder.restorePassword.emailForRestoration

import com.toosafinder.api.*
import com.toosafinder.api.login.PasswordRestoreReq
import com.toosafinder.utils.UnitOption

class EmailForRestorationRepository(
    private val api: ApiClient
) {

    suspend fun restorePassword (email : String): UnitOption<ErrorCode?> =
        api.postUnit("/user/restore-password", PasswordRestoreReq(email), withAuth = false)
            .transform(
                onSuccess = { UnitOption.success() },
                onConflict = { UnitOption.error(ErrorCode.fromString(it.code)) }
            )

}
