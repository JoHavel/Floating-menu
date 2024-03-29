plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(29)
    buildToolsVersion = "29.0.2"


    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk7", embeddedKotlinVersion)
    implementation("androidx.appcompat", "appcompat", "1.1.0-rc01")
    implementation("androidx.core", "core-ktx", "1.2.0-alpha02")
    androidTestImplementation("androidx.test", "runner", "1.3.0-alpha02")
    androidTestImplementation("androidx.test.espresso", "espresso-core", "3.3.0-alpha02")
    androidTestImplementation("androidx.test.ext", "junit", "1.1.1")
    androidTestImplementation("androidx.test", "core", "1.0.0-beta02")
}
