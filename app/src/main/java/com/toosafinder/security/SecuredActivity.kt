package com.toosafinder.security

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.toosafinder.login.LoginActivity
import org.koin.android.ext.android.getKoin

/**
 * Базовая активити, которая проверяет права пользователя перед тем как запуститься
 */
abstract class SecuredActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val userSession = getKoin().get<UserSession>()
        if(!allowAccess(userSession)) onAccessDeniedAction()
    }

    open fun onAccessDeniedAction(): () -> Unit = ::startLoginActivity

    private fun startLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    open fun allowAccess(userSession: UserSession): Boolean {
        return userSession.isOpened()
    }
}
