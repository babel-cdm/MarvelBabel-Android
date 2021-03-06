plugins {
    id(GradlePluginId.ANDROID_APP)
    id(GradlePluginId.BASE_GRADLE_PLUGIN)
}

android.buildFeatures {
    viewBinding = true
}

dependencies {
    implementation(project(ModulesDependency.CACHE))
    implementation(project(ModulesDependency.REMOTE))
    implementation(project(ModulesDependency.DATA))
    implementation(project(ModulesDependency.DOMAIN))
    implementation(project(ModulesDependency.PRESENTATION))
}
