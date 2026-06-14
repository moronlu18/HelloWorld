plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("org.jetbrains.dokka")
    id("com.google.gms.google-services")
    // Add the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics")
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
}


tasks.dokkaHtml.configure {
    outputDirectory.set(file("../documentation/html"))
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
    dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:1.9.20")
    // Is applied universally
    dokkaPlugin("org.jetbrains.dokka:mathjax-plugin:1.9.20")
    // Is applied for the single-module dokkaHtml task only
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.9.20")
    // Is applied for HTML format in multi-project builds
    dokkaHtmlPartialPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.9.20")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")

}