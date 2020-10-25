package com.toosafinder.emailConfirmation

import com.toosafinder.network.HTTPRes
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import java.util.*

private const val checkURL : String = "/user/emailConfirmed"
interface EmailConfirmationDataSource {

    @GET(checkURL)
    suspend fun checkEmailToken(emailToken : UUID) : HTTPRes<Unit>
}