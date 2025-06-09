import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}
tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}
group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation("org.jsoup:jsoup:1.15.4") //Extracting from html
    implementation("org.seleniumhq.selenium:selenium-chrome-driver:4.21.0") //JS-site scrapanje
    implementation("org.jetbrains.compose.material3:material3-desktop:1.5.10") //Za design
    implementation("org.mongodb:mongodb-driver-sync:4.11.0")
    implementation("org.json:json:20231013")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.foundation)
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "WebScraper"
            packageVersion = "1.0.0"
        }
    }
}
