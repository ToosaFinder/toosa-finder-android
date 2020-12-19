package com.toosafinder.security

import org.koin.dsl.module

val securityModule = module {
    single { UserSession() }
}