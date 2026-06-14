plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.dokka)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.moronlu18.helloworld"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moronlu18.helloworld"
        minSdk = 26
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}


tasks.dokkaHtml.configure {
    outputDirectory.set(file("${rootProject.projectDir}/docs"))
}
/*
tasks.dokkaJavadoc.configure {
    outputDirectory.set(file("../documentation/javadoc"))
}*/
dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.core.ktx)
    //Para documentar el proyecto con javadoc se debe añadir la siguiente librería
    //implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    //implementation(files("/home/lourdes/Android/Sdk/platforms/android-33/android.jar"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Librería para documentar con Dokka
    dokkaPlugin(libs.dokka.android.documentation)
    // Is applied universally
    dokkaPlugin(libs.dokka.mathjax)
    // Is applied for the single-module dokkaHtml task only
    dokkaHtmlPlugin(libs.dokka.kotlin.java)
    // Is applied for HTML format in multi-project builds
    dokkaHtmlPartialPlugin(libs.dokka.kotlin.java)

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")

}
