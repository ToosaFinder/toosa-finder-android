package com.toosafinder.emailConfirmation

import com.toosafinder.network.HTTPRes
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

class EmailConfirmationRepository(
    private val emailConfirmationDataSource: EmailConfirmationDataSource) {

    suspend fun checkEmailToken(emailToken : UUID) : Boolean =
        when(emailConfirmationDataSource.checkEmailToken(emailToken)){
            is HTTPRes.Success -> true
            is HTTPRes.Conflict -> false
        }


}