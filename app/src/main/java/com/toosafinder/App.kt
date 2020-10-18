package com.toosafinder

import android.app.Application
import com.toosafinder.data.LoginDataSource
import com.toosafinder.data.LoginRepository
import com.toosafinder.login.LoginViewModel
import com.toosafinder.registration.RegistrationRepository
import com.toosafinder.registration.RegistrationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Тут мы управляем зависимостями средствами Koin
 */
val loginModule = module {
    single { LoginDataSource() }
    single { LoginRepository(get()) }
    single { LoginViewModel(get()) }
    single { RegistrationRepository() }
    single { RegistrationViewModel(get()) }
}

/**
 * Кастомный Application класс нужен чтобы Koin запустить
 */
class App: Application() {
    override fun onCreate(){
        super.onCreate()
        startKoin {
            // Android context
            androidContext(this@App)
            // modules
            modules(loginModule)
        }
    }
}