buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.9.10"
    var koinVersion: String by extra
    koinVersion = "3.4.0"
    var koinCoreVersion: String by extra
    koinCoreVersion = "3.4.0"

    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
