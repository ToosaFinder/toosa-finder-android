package com.toosafinder.emailVerification

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.R

class emailVerificationActivity : AppCompatActivity() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        //вот тут нужен какой-то фон, надо узнать у пацанов
        setContentView(R.layout.activity_login)

        //val action: String? = intent?.action
        val data: Uri? = intent?.data

        val Id : String = parceData(data)

    }

    private fun parceData(data : Uri?) : String {
        if(data?.lastPathSegment!=null)
        return data.lastPathSegment!!
        //вопрос : что делать в случае, если пришел мусор в ссылке в последнем сегменте
        else
            return "incorrect message"
    }

}