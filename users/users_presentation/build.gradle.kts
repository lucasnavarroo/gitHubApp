apply {
    from("$rootDir/base-module.gradle")
}
dependencies {
    "implementation"(project(":core"))
    "implementation"(project(":users:users_domain"))
}

