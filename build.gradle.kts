import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("org.jetbrains.dokka") version "1.9.20"
    id("com.google.gms.google-services") version "4.4.2" apply false
    // Add the dependency for the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
}

tasks.withType<DokkaTask>().configureEach {
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        //customAssets = listOf(file("my-image.png"))
        //customStyleSheets = listOf(file("my-styles.css"))
        footerMessage = "(c) 2022 Lourdes Rodríguez Morón"
        separateInheritedMembers = false
        //templatesDir = file("dokka/templates")
        mergeImplicitExpectActualDeclarations = false
    }
}

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:dokka-base:1.9.20")
    }
}


