// Plugins
plugins {
    alias(libs.plugins.android.application)
}

// Config Android
android {
    namespace = "com.example.flexfilmes"

    // SDK de compilação
    compileSdk = 36

    // Configurações padrão
    defaultConfig {
        applicationId = "com.example.flexfilmes"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        // Runner de testes
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Tipos de build
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Compatibilidade Java
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// Dependências
dependencies {

    // UI
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.play.services.maps3d)

    // Image Loading
    implementation(libs.glide)
    annotationProcessor(libs.glide)

    // Testes
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}