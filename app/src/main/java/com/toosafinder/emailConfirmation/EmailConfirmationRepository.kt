package com.toosafinder.emailConfirmation

import android.util.Log
import com.toosafinder.network.HTTPRes
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

class EmailConfirmationRepository(
    private val emailConfirmationDataSource: EmailConfirmationDataSource) {

    suspend fun checkEmailToken(emailToken : UUID) =
        when(val mes : HTTPRes<Unit> = emailConfirmationDataSource.checkEmailToken(emailToken)){
            is HTTPRes.Success -> Log.d("SuccessfulConfirmation","Success")
            is HTTPRes.Conflict -> Log.d("ConfirmationError", mes.code)
        }


}