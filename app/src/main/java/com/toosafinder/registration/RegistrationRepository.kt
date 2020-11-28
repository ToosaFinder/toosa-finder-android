package com.toosafinder.registration

import com.toosafinder.api.ApiClient
import com.toosafinder.api.ErrorCode
import com.toosafinder.api.registration.UserRegistrationReq
import com.toosafinder.utils.Option
import com.toosafinder.api.post
import com.toosafinder.utils.UnitOption

class RegistrationRepository(
    private val api: ApiClient
) {

    suspend fun registerUser(email: String, login: String, password: String): UnitOption<ErrorCode?> {
        return api.post<Unit>("/user/registration", UserRegistrationReq(email, login, password), withAuth = false)
            .transform(
                onSuccess = { UnitOption.success() },
                onConflict = { UnitOption.error(ErrorCode.fromString(it.code)) }
            )
    }
}