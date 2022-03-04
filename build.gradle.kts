import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.6.10"
	jacoco
	id("org.sonarqube") version "3.3"
}

group = "com.gildedrose"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib"))
	testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
}

tasks.test {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
	}
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
	dependsOn(tasks.test) // tests are required to run before generating the report
}

jacoco {
	toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
	reports {
		xml.required.set(true)
	}
}

// config JVM target to 1.8 for kotlin compilation tasks
tasks.withType<KotlinCompile>().configureEach {
	kotlinOptions.jvmTarget = "1.8"
}

sonarqube {
	properties {
		property("sonar.projectKey", "joost-van-weenen_GildedRose-Refactoring-Kata-1")
		property("sonar.organization", "joost-van-weenen")
		property("sonar.host.url", "https://sonarcloud.io")
	}
}