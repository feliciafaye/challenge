plugins {
	id(Dependencies.Android.Plugin.library)
	id(Dependencies.Kotlin.Android.plugin)
	id(Dependencies.Android.Hilt.Plugin.name)
	kotlin("kapt")
}

android {
	namespace = "${Configuration.namespace}.model"
	compileSdk = Configuration.compileSdk
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
}

dependencies {
	implementation(project(Module.network))

	implementation(Dependencies.Android.Hilt.hiltAndroid)
	kapt(Dependencies.Android.Hilt.hiltCompiler)

	implementation(Dependencies.Android.Paging3.runtime)
	implementation(Dependencies.Android.Paging3.compose)

	implementation(Dependencies.arrowKt)
}