object TestLibraryDependency{
    object Version{
        const val JUNIT = "4.13.1"
        const val JUNIT_ANDROID = "1.1.1"
        const val ESPRESSO = "3.3.0"
        const val MOCKITO = "3.8.0"
        const val MOCKITO_KOTLIN = "2.2.0"
    }

    const val JUNIT = "junit:junit:${Version.JUNIT}"
    const val JUNIT_ANDROID = "androidx.test.ext:junit:${Version.JUNIT_ANDROID}"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:${Version.ESPRESSO}"
    const val KOIN ="org.koin:koin-test:${LibraryDependency.Version.KOIN}"
    const val MOCKITO = "org.mockito:mockito-core:${Version.MOCKITO}"
    const val MOCKITO_INLINE = "org.mockito:mockito-inline:${Version.MOCKITO}"
    const val MOCKITO_KOTLIN = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Version.MOCKITO_KOTLIN}"
}