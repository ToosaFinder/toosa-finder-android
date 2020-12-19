package com.toosafinder

import android.app.Application
import com.toosafinder.MainScreen.MapMainScreen.MapMainScreenRepository
import com.toosafinder.MainScreen.MapMainScreen.MapMainScreenViewModel
import com.toosafinder.api.ApiClient
import com.toosafinder.api.httpClient
import com.toosafinder.emailConfirmation.EmailConfirmationRepository
import com.toosafinder.emailConfirmation.EmailConfirmationViewModel
import com.toosafinder.eventCreation.EventCreationViewModel
import com.toosafinder.eventCreation.EventRepository
import com.toosafinder.login.LoginRepository
import com.toosafinder.security.UserSession
import com.toosafinder.login.LoginViewModel
import com.toosafinder.registration.RegistrationRepository
import com.toosafinder.registration.RegistrationViewModel
import com.toosafinder.restorePassword.emailForRestoration.EmailForRestorationRepository
import com.toosafinder.restorePassword.emailForRestoration.EmailForRestorationViewModel
import com.toosafinder.restorePassword.restorePassword.RestorePasswordRepository
import com.toosafinder.restorePassword.restorePassword.RestorePasswordViewModel
import com.toosafinder.security.securityModule
import io.ktor.util.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

private const val apiBaseUrl = "http://34.68.138.148"

@KtorExperimentalAPI
private val apiModule = module {
    single { httpClient() }
    single {
        val userSession = get<UserSession>()
        val tokenProvider = {
            userSession.accessToken()
                ?: error("user session is not opened")
        }
        ApiClient(get(), apiBaseUrl, tokenProvider)
    }
    single {
//        val mapper = JsonMapper.builder().addModule(JavaTimeModule())
//        jsonMapper{
//            addModule(JavaTimeModule())
//        }
//        JsonMapper.builder().findAndAddModules().build()
//        val javaTimeModule = JavaTimeModule()
//        javaTimeModule.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())
    }
}

private val loginModule = module {
    single { LoginRepository(get(), get()) }
    single { LoginViewModel(get()) }
}

private val emailForRestorationModule = module {
    single { EmailForRestorationRepository(get()) }
    single { EmailForRestorationViewModel(get()) }
}

private val restorePasswordModule = module {
    single { RestorePasswordRepository(get()) }
    single { RestorePasswordViewModel(get()) }
}

private val registrationModule = module {
    single { RegistrationRepository(get()) }
    single { RegistrationViewModel(get()) }
}

private val emailConfirmationModule = module {
    single { EmailConfirmationRepository(get()) }
    single { EmailConfirmationViewModel(get()) }
}

private val mapMainScreenModule = module {
    single { MapMainScreenRepository(get()) }
    single { MapMainScreenViewModel(get()) }
}

private val eventCreationModule = module {
    single { EventRepository(get()) }
    single { EventCreationViewModel(get()) }
}

@KtorExperimentalAPI
class App: Application() {
    override fun onCreate(){
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                apiModule,
                loginModule,
                restorePasswordModule,
                registrationModule,
                emailForRestorationModule,
                emailConfirmationModule,
                securityModule,
                mapMainScreenModule,
                eventCreationModule
            )
        }
    }
}


