package com.toosafinder.data

import com.toosafinder.data.model.LoggedInUser
import kotlinx.coroutines.delay
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    /**
     * Try-catch точно исчезнет в дальнейшем, потому что ошибки обраобтает API-CLIENT
     */
    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            /**
             * вместо delay тут будет вызов ClientApi.
             * Не уверен пока в необходимости слоя DataSource в нашем случае.
             */
            delay(timeMillis = 3000)
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe", "123")
            return Result.Success(fakeUser)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}