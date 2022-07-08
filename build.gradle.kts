import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("org.springframework.boot") version "2.2.1.BUILD-SNAPSHOT"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.6.20"
    kotlin("plugin.spring") version "1.6.20"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.6.20" apply false
    id("io.gitlab.arturbosch.detekt") version "1.20.0"
    id("org.owasp.dependencycheck") version "7.1.1"
    id("com.github.ben-manes.versions") version "0.42.0"
    jacoco
}

allprojects {
    group = "com.stringconcat"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }

    apply(plugin = "io.gitlab.arturbosch.detekt")
    apply(plugin = "org.owasp.dependencycheck")
    apply(plugin = "com.github.ben-manes.versions")
    apply(plugin = "jacoco")

    detekt {
        buildUponDefaultConfig = true
        allRules = false
        config = files("$rootDir/config/detekt.yml")
    }

    tasks.withType<Detekt>().configureEach {
        reports {
            html.required.set(true)
        }
    }

    tasks.withType<Detekt>().configureEach {
        jvmTarget = "1.8"
    }

    jacoco {
        toolVersion = "0.8.7"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

}

java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

dependencies {
    // spring modules
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation(project(":presentation"))
    implementation(project(":persistence"))
    implementation(project(":useCasePeople"))
    implementation(project(":businessPeople"))
    implementation(project(":quoteGarden"))
    implementation(project(":avatarsDicebear"))

    // dev tools
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    //persistance
    implementation("org.postgresql:postgresql:42.3.4")
    implementation("org.liquibase:liquibase-core:4.9.1")

    // tests
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("io.projectreactor:reactor-test")

}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.5".toBigDecimal()
            }
        }

        rule {
            isEnabled = false
            element = "CLASS"
            includes = listOf("org.gradle.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.3".toBigDecimal()
            }
        }
    }
}


tasks.jacocoTestReport {
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}
