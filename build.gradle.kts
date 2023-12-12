import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.*

plugins {
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"

	kotlin("jvm") version "1.9.20"
	kotlin("plugin.spring") version "1.9.20"
	kotlin("plugin.jpa") version "1.9.20"
}

group = "com.slog"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// tomcat setting
	implementation("org.springframework.boot:spring-boot-starter-web")

	// JPA setting
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	// lombok setting
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// h2 setting
	runtimeOnly("com.h2database:h2")
}

val frontendDir = "$projectDir/src/main/frontend"
val npm = if (System.getProperty("os.name").lowercase(Locale.ROOT).contains("windows")) "npm.cmd" else "npm"

tasks {
	val setupReact by creating(Exec::class) {
		workingDir = file(frontendDir)
		inputs.dir(file(frontendDir))
		group = BasePlugin.BUILD_GROUP

		commandLine(npm, "audit", "fix")
		commandLine(npm, "install")
		commandLine(npm, "run-script", "build")
	}

	val copyReactBuildFiles by creating(Copy::class) {
		from("$frontendDir/build")
		into("$projectDir/src/main/resources/static")
	}

	withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "17"
		}
	}
	withType<Test> {
		useJUnitPlatform()
	}
	processResources {
//		dependsOn("setupReact")
//		dependsOn("copyReactBuildFiles")
	}
}
