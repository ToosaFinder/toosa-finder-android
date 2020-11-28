package com.toosafinder.emailConfirmation

import com.toosafinder.api.ApiClient
import com.toosafinder.api.ErrorCode
import com.toosafinder.api.put
import com.toosafinder.utils.UnitOption
import java.util.UUID

class EmailConfirmationRepository(
    private val api: ApiClient
) {

    suspend fun checkEmailToken(emailToken : UUID): UnitOption<ErrorCode?> {
        return api.put<Unit>("/user/email-confirmed/$emailToken")
            .transform(
                onSuccess = { UnitOption.success() },
                onConflict = { UnitOption.error(ErrorCode.fromString(it.code)) }
            )
    }
}
