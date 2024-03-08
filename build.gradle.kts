repositories {
    mavenCentral()
    mavenLocal()
}

plugins {
    id("java")
    id("groovy")
    id("ru.kazantsev.nsd.sdk.gradle_plugin") version "1.0.0"
}

group = "ru.kazantsev.nsd"
version = "1.0"

dependencies {
    implementation("org.codehaus.groovy:groovy-all:3.0.19")
    implementation("org.slf4j:slf4j-api:2.0.9")

    implementation ("org.apache.poi:poi-ooxml:5.2.4")

    testImplementation("ch.qos.logback:logback-classic:1.4.11")
    testImplementation("ch.qos.logback:logback-core:1.2.9")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.compileJava {
    options.encoding = "UTF-8"
}

sourceSets {
    main {
        groovy {
            srcDirs("src/main/groovy")
        }
    }
}

fakeClasses {
    generate("DSO_TEST")
    targetMeta = setOf("orderCall")
}