plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
    id("io.codearte.nexus-staging")
    signing
}

kotlin {
    universalLib()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-serialization-core:${vers.kotlinx.serialization}")
            }
        }

        val commonTest by getting {
            dependencies {
                api(asoft("test", vers.asoft.test))
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:${vers.kotlinx.serialization}")
            }
        }
    }
}

aSoftLibrary(
    version = vers.asoft.money,
    description = "A multiplatfrom library for dealing with money and currency"
)