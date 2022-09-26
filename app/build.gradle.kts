plugins {
	id(Dependencies.Android.Plugin.application)
	id(Dependencies.Kotlin.Android.plugin)
	id(Dependencies.Android.Hilt.Plugin.name)
	kotlin("kapt")
}

android {
	namespace = Configuration.namespace
	compileSdk = Configuration.compileSdk

	defaultConfig {
		applicationId = "io.faye.music"
		minSdk = Configuration.minSdk
		targetSdk = Configuration.compileSdk
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
		kotlinOptions {
			freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
		}
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = "1.2.0" //Dependencies.Android.Compose.version
	}
	packagingOptions {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {
	implementation(project(Module.model))

	implementation(Dependencies.arrowKt)
	implementation(Dependencies.coil)

	implementation(Dependencies.Android.coreKtx)
	implementation(Dependencies.Android.lifecycleRuntimeKtx)
	implementation(Dependencies.Android.activiyCompose)
	implementation(Dependencies.Android.material)

	implementation(Dependencies.Android.Compose.ui)
	implementation(Dependencies.Android.Compose.viewModel)
	implementation(Dependencies.Android.Compose.uiToolingPreview)

	debugImplementation(Dependencies.Android.Compose.uiTooling)
	debugImplementation(Dependencies.Android.Compose.uiTestManifest)

	implementation(Dependencies.Android.Hilt.hiltAndroid)
	kapt(Dependencies.Android.Hilt.hiltCompiler)

	implementation(Dependencies.Android.Paging3.compose)
	implementation(Dependencies.Android.Paging3.runtime)

	testImplementation(Dependencies.junit4)
}