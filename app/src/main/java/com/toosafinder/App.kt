package com.toosafinder

import android.app.Application
import com.toosafinder.data.LoginDataSource
import com.toosafinder.data.LoginRepository
import com.toosafinder.emailConfirmation.EmailConfirmationDataSource
import com.toosafinder.emailConfirmation.EmailConfirmationRepository
import com.toosafinder.emailConfirmation.EmailConfirmationViewModel
import com.toosafinder.login.LoginApi
import com.toosafinder.login.LoginRepository
import com.toosafinder.login.LoginViewModel
import com.toosafinder.network.ErrorHandlingInterceptor
import com.toosafinder.network.provideOkHttpClient
import com.toosafinder.network.provideRetrofit
import com.toosafinder.registration.RegistrationRepository
import com.toosafinder.registration.RegistrationViewModel
import com.toosafinder.restorePassword.emailForRestoration.EmailForRestorationDataSource
import com.toosafinder.restorePassword.emailForRestoration.EmailForRestorationRepository
import com.toosafinder.restorePassword.emailForRestoration.EmailForRestorationViewModel
import com.toosafinder.restorePassword.restorePassword.RestorePasswordDataSource
import com.toosafinder.restorePassword.restorePassword.RestorePasswordRepository
import com.toosafinder.restorePassword.restorePassword.RestorePasswordViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { ErrorHandlingInterceptor() }
    factory { provideOkHttpClient(get()) }
    single { provideRetrofit(get()) }

    single {
        get<Retrofit>().create(LoginApi::class.java)
    }
    single {
        get<Retrofit>().create(EmailForRestorationDataSource::class.java)
    }
    single {
        get<Retrofit>().create(RestorePasswordDataSource::class.java)
    }
    single {
        get<Retrofit>().create(EmailConfirmationDataSource::class.java)
    }
}

/**
 * Тут мы управляем зависимостями средствами Koin
 */
val loginModule = module {
    single { LoginRepository(get()) }
    single { LoginViewModel(get()) }
    single { RegistrationRepository() }
    single { RegistrationViewModel(get()) }
    single { RestorePasswordRepository(get()) }
    single { RestorePasswordViewModel(get()) }
    single { EmailForRestorationRepository(get()) }
    single { EmailForRestorationViewModel(get()) }
}

val emailConfirmationModule = module {
    single { EmailConfirmationRepository(get()) }
    single { EmailConfirmationViewModel(get()) }
}

/**
 * Кастомный Application класс нужен чтобы Koin запустить
 */

class App: Application() {
    override fun onCreate(){
        super.onCreate()
        startKoin {
            androidContext(this@App)
            // modules
            modules(loginModule)
        }
    }
}