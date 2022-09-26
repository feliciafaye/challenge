plugins {
	id(Dependencies.Android.Plugin.library)
	id(Dependencies.Kotlin.Android.plugin)
	id(Dependencies.Kotlin.Serialization.plugin)
	id(Dependencies.Android.Hilt.Plugin.name)
	kotlin("kapt")
}

android {
	namespace = "${Configuration.namespace}.network"
	compileSdk = Configuration.compileSdk
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
		kotlinOptions {
			freeCompilerArgs = listOf("-opt-in=kotlin.RequiresOptIn")
		}
	}
}

dependencies {
	implementation(Dependencies.Android.Hilt.hiltAndroid)
	kapt(Dependencies.Android.Hilt.hiltCompiler)

	implementation(Dependencies.Ktor.client)
	implementation(Dependencies.Ktor.engine)
	implementation(Dependencies.Ktor.contentNegotiation)
	implementation(Dependencies.Ktor.kotlinXJson)

	implementation(Dependencies.arrowKt)
}