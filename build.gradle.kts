import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.dokka)
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
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
        classpath(libs.dokka.android.documentation)
    }
}
