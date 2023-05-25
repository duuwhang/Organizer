@file:Suppress("UnstableApiUsage")

val kotlinVersion: String by rootProject.extra
val koinVersion: String by rootProject.extra
val koinCoreVersion: String by rootProject.extra

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 33
    buildToolsVersion = "33.0.0"

    defaultConfig {
        applicationId = "com.organizer"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        applicationVariants.all {
            outputs.all {
                if (name.contains("release"))
                    (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName = "${rootProject.name}.apk"
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_18
    }

    sourceSets {
        getByName("main") {
            kotlin.srcDir("src/main/kotlin")
        }
    }
    namespace = "com.organizer"
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs = listOf("-Xlint:all", "-Xlint:unchecked", "-Xlint:deprecation")
}

tasks.withType<Test>{
    useJUnitPlatform()
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("io.insert-koin:koin-core:$koinCoreVersion")
    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-android-compat:$koinVersion")
    implementation(kotlin("reflect"))

    testImplementation("io.insert-koin:koin-test:$koinCoreVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation("io.mockk:mockk:1.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
