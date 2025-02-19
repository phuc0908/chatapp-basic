plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.chatapp_dacs3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.chatapp_dacs3"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-messaging:24.0.0")
    //    plugins {
//        id("com.android.application")
//        id("org.jetbrains.kotlin.android")
//        id("com.google.devtools.ksp")
//        id("com.google.gms.google-services")
//        id("kotlin-kapt")
//        id("com.google.dagger.hilt.android")
//    }

    android {
        namespace = "com.example.chatapp"
        compileSdk = 34

        defaultConfig {
            applicationId = "com.example.chatapp"
            minSdk = 24
            targetSdk = 34
            versionCode = 1
            versionName = "1.0"

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
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
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
        kotlinOptions {
            jvmTarget = "1.8"
        }
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.1"
        }
        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    dependencies {

        implementation("androidx.core:core-ktx:1.13.1")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
        implementation("androidx.activity:activity-compose:1.9.0")
        implementation(platform("androidx.compose:compose-bom:2023.08.00"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3")
        implementation("com.google.firebase:firebase-auth:23.0.0")
        implementation("com.google.firebase:firebase-database:21.0.0")
        implementation("com.google.firebase:firebase-storage:21.0.0")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")
        implementation ("androidx.navigation:navigation-compose:2.7.7")

        implementation("com.fatherofapps:jnav:1.0.1")
        ksp("com.fatherofapps:jnav:1.0.1")

//    W L Project ytb
        implementation ("androidx.navigation:navigation-compose:2.7.7")
        implementation ("com.google.dagger:hilt-android:2.49")
        implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")
//        kapt ("com.google.dagger:hilt-android-compiler:2.49")
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
        // Activity Result API
        implementation ("androidx.activity:activity-ktx:1.9.0")
        implementation ("androidx.activity:activity-compose:1.9.0")

        implementation ("io.coil-kt:coil-compose:2.6.0")

        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

//        Video Mes
        implementation ("com.google.android.exoplayer:exoplayer:2.19.1")

        implementation("io.sanghun:compose-video:1.2.0")
        implementation("androidx.media3:media3-exoplayer:1.3.1") // [Required] androidx.media3 ExoPlayer dependency
        implementation("androidx.media3:media3-session:1.3.1") // [Required] MediaSession Extension dependency
        implementation("androidx.media3:media3-ui:1.3.1") // [Required] Base Player UI

        implementation("androidx.media3:media3-exoplayer-dash:1.3.1") // [Optional] If your media item is DASH
        implementation("androidx.media3:media3-exoplayer-hls:1.3.1") // [Optional] If your media item is HLS (m3u8..)

    }
}