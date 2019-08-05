import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.1.6.RELEASE"
	id("io.spring.dependency-management") version "1.0.7.RELEASE"
	kotlin("jvm") version "1.3.41"
	kotlin("plugin.spring") version "1.3.41"
}

group = "com.github.hirokazumiyaji"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
	implementation("io.lettuce:lettuce-core:5.1.8.RELEASE")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.google.flogger:flogger:0.4")
	implementation("com.google.flogger:flogger-system-backend:0.4")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter:5.5.1")
	testImplementation("org.testcontainers:testcontainers:1.11.3")
	testImplementation("org.testcontainers:junit-jupiter:1.11.3")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.test {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed")
	}
}
