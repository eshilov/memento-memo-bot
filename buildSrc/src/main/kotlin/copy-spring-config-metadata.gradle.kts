import org.gradle.api.tasks.Copy

val taskName = "copyConfigMetadata"

val metadataRootPath = "${project.buildDir}/tmp/kapt3/classes"
val sourcePath = "$metadataRootPath/main/META-INF/spring-configuration-metadata.json"
val destinationPath = "$metadataRootPath/test/META-INF"

tasks.register<Copy>(taskName) {
    from(file(sourcePath))
    into(file(destinationPath))
}

tasks.named("compileTestKotlin") {
    finalizedBy(taskName)
}

tasks.named("test") {
    dependsOn(taskName)
}