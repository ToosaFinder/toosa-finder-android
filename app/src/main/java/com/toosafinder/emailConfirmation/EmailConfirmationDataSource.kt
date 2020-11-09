package com.toosafinder.emailConfirmation

import com.toosafinder.network.HTTPRes
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*

private const val checkURL : String = "/user/email-confirmed/{emailToken}"
interface EmailConfirmationDataSource {

    @PUT(checkURL)
    suspend fun checkEmailToken(@Path("emailToken") emailToken : UUID) : HTTPRes<Unit>
}