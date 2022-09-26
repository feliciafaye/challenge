object Dependencies {

	val arrowKt = "io.arrow-kt:arrow-core:1.1.3-rc.1"
	val coil = "io.coil-kt:coil-compose:2.2.1"



	val junit4 = "junit:junit:4.13.2"

	object Java {
		object Plugin {
			val name = "java-library"
		}
	}

	object Kotlin {
		val version = "1.7.0"

		object Android {
			val plugin = "org.jetbrains.kotlin.android"
		}

		object Jvm {
			val plugin = "org.jetbrains.kotlin.jvm"
		}

		object Serialization {
			const val plugin = "kotlinx-serialization"
			val gradleClasspath = "org.jetbrains.kotlin:kotlin-serialization:$version"

		}
	}

	object Android {
		object Plugin {
			val version = "7.3.0"
			val application = "com.android.application"
			val library = "com.android.library"
		}


		val coreKtx = "androidx.core:core-ktx:1.9.0"
		val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
		val activiyCompose = "androidx.activity:activity-compose:1.5.1"
		val material = "androidx.compose.material:material:1.2.1"

		val extJunit = "androidx.test.ext:junit:1.1.3"
		val expressoCore = "androidx.test.espresso:espresso-core:3.4.0"

		object Compose {
			val version = "1.2.1"

			val ui = "androidx.compose.ui:ui:$version"
			val uiTooling = "androidx.compose.ui:ui-tooling:$version"
			val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
			val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
			val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"


			val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4:$version"
		}

		object Hilt {
			private const val version: String = "2.43.2"

			object Plugin {
				const val name: String = "dagger.hilt.android.plugin"
				const val gradleClasspath: String = "com.google.dagger:hilt-android-gradle-plugin:$version"
			}

			const val hiltAndroid = "com.google.dagger:hilt-android:$version"
			const val hiltCompiler = "com.google.dagger:hilt-compiler:$version"
		}

		object Paging3 {
			const val runtime = "androidx.paging:paging-runtime:3.1.1"
			const val compose = "androidx.paging:paging-compose:1.0.0-alpha15"
		}
	}

	object Ktor {
		private const val version = "2.1.1"
		const val client = "io.ktor:ktor-client-core:$version"
		const val engine = "io.ktor:ktor-client-android:$version"
		const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
		const val kotlinXJson = "io.ktor:ktor-serialization-kotlinx-json:$version"
	}
}