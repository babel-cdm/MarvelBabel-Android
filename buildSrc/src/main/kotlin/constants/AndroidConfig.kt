object AndroidConfig {

    const val COMPILE_SDK_VERSION = 30
    const val MIN_SDK_VERSION = 26
    const val TARGET_SDK_VERSION = 30
    const val VERSION_CODE = 2
    const val VERSION_NAME = "1a"

    const val ID = "com.babel.marvel"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
}


interface BuildType {

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }

}