apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(":users:users_domain"))
    "implementation"(project(":core"))

    "implementation"("com.squareup.retrofit2:retrofit:2.9.0")
    "implementation"("com.squareup.retrofit2:converter-gson:2.9.0")
    "implementation"("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    "implementation"("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
}