plugins {
	java
	id("org.springframework.boot") version "2.7.16-SNAPSHOT"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_11
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("com.google.guava:guava:31.1-jre")
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	implementation ("io.jsonwebtoken:jjwt-impl:0.11.2")
	implementation ("io.jsonwebtoken:jjwt-jackson:0.11.2")
	implementation("org.springframework.boot:spring-boot-starter-security:2.7.6")


}

tasks.withType<Test> {
	useJUnitPlatform()
}
