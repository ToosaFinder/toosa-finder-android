package com.toosafinder.restorePassword.restorePassword

import com.toosafinder.api.*
import com.toosafinder.api.login.PasswordSetReq
import com.toosafinder.utils.UnitOption

class RestorePasswordRepository(
    private val api: ApiClient
) {

    suspend fun registerPassword(emailToken : String, password : String) : UnitOption<ErrorCode?> =
        api.postUnit("/user/set-password", PasswordSetReq(emailToken, password), withAuth = false)
            .transform(
                onSuccess = { UnitOption.success() },
                onConflict = { UnitOption.error(ErrorCode.fromString(it.code)) }
            )

}