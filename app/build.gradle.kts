plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.walmartassessment"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.walmartassessment"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //retrofit
    implementation (libs.androidx.retrofit)
    //Gson -> json data to java or kotlin format
    implementation (libs.androidx.gson.converter)
    implementation(libs.androidx.logging.interceptor)

    // viewmodel scope
    implementation (libs.androidx.viewmodel.scope)
    // MockK for Android instrumentation tests (if needed)
    androidTestImplementation(libs.androidx.mockk)
    // LiveData & ViewModel testing
    testImplementation(libs.androidx.arch.core)
    // Turbine for Flow testing (Highly recommended)
    testImplementation(libs.androidx.turbine)
    testImplementation(libs.androidx.coroutine.test)

    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.7.2")
    testImplementation ("org.junit.jupiter:junit-jupiter-engine:5.7.2")
    testImplementation("io.mockk:mockk:1.13.4")

}