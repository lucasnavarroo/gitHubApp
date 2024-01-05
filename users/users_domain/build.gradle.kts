    apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(":core"))
    "implementation"("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
}