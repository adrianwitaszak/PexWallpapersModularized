import commons.addHiltDependenciesBasic
import dependencies.TestDependencies

plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.DAGGER_HILT)
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }
    kotlinOptions {
        freeCompilerArgs + "-Xopt-in=androidx.paging.ExperimentalPagingApi"
        freeCompilerArgs + "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
    }
}

hilt {
    enableExperimentalClasspathAggregation = true
}

dependencies {

    implementation(project(Modules.COMMON))
    api(project(Modules.DATA))
    implementation("androidx.paging:paging-common-ktx:3.0.1")

    addHiltDependenciesBasic()

    implementation(TestDependencies.coroutinesTest)
    implementation(TestDependencies.truth)
    implementation(TestDependencies.test_runner)
    implementation(TestDependencies.test_core)
    implementation(TestDependencies.mockk)

    testImplementation(TestDependencies.hiltTest)
    kaptTest(TestDependencies.hiltTestCompiler)
}