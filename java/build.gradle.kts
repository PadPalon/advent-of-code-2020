plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    // This dependency is used by the application.
    implementation("com.google.guava:guava:29.0-jre")
    implementation("org.javatuples:javatuples:1.2")


    // Use TestNG framework, also requires calling test.useTestNG() below
    testImplementation("org.testng:testng:7.2.0")
}

application {
    // Define the main class for the application.
    mainClassName = "ch.neukom.App"
}

val test by tasks.getting(Test::class) {
    // Use TestNG for unit tests
    useTestNG()
}
