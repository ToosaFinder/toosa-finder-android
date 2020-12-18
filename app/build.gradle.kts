val kotlin_version: String by extra
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

apply {
    plugin("kotlin-android")
}

android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.2"

    defaultConfig {
        applicationId = "com.toosafinder"
        minSdkVersion(24)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${System.getProperty("kotlinVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    implementation("com.fasterxml.jackson.core:jackson-core:2.11.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.2")

    implementation("com.android.support:multidex:2.0.1")
    //TODO: брать из мевен репо
    implementation(files("../libs/api-model-0.0.2.jar"))

    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.annotation:annotation:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.1")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    val ktorVersion = "1.4.2"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-android:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-json:$ktorVersion")
    implementation("io.ktor:ktor-client-jackson:$ktorVersion")

    val koinVersion = "2.1.6"
    implementation("org.koin:koin-android:$koinVersion")
    implementation("org.koin:koin-android-scope:$koinVersion")
    implementation("org.koin:koin-android-viewmodel:$koinVersion")
    implementation("org.koin:koin-android-ext:$koinVersion")
    implementation("androidx.core:core-ktx:+")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")

    implementation("co.lujun:androidtagview:1.1.7")
    implementation("com.google.maps.android:android-maps-utils:2.2.0")
    implementation("com.google.maps.android:maps-utils-ktx:2.2.0")

    testImplementation("junit:junit:4.13")

}
repositories {
    mavenCentral()
    maven("https://maven.pkg.github.com/toosafinder/api-model-jvm") {

    }
}