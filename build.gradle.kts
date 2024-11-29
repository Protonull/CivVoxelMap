plugins {
    id("fabric-loom") version "1.8-SNAPSHOT"
}

version = "${rootProject.extra["mod_version"]}-${project.extra["minecraft_version"]}"
group = "${rootProject.extra["maven_group"]}.mod.fabric"

base {
    archivesName.set(project.extra["archives_base_name"] as String)
}

loom {
    accessWidenerPath = file("src/main/resources/civvoxelmap.accesswidener")
}

dependencies {
    minecraft("com.mojang:minecraft:${project.extra["minecraft_version"]}")
    loom {
        @Suppress("UnstableApiUsage")
        mappings(layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:${project.extra["parchment_name"]}:${project.extra["parchment_version"]}@zip")
        })
    }

    modImplementation("net.fabricmc:fabric-loader:${project.extra["fabric_loader_version"]}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.extra["fabric_api_version"]}")

    // https://modrinth.com/mod/voxelmap-updated/version/1.21-1.13.1
    "maven.modrinth:voxelmap-updated:QZuVdvFR".also {
        modImplementation(it)
        include(it)
    }

    // https://modrinth.com/mod/modmenu/version/12.0.0-beta.1
    // TODO: This is a beta, update as soon as there's a release!
    "maven.modrinth:modmenu:PcJvQYqu".also {
        modCompileOnly(it)
        modLocalRuntime(it)
    }

    // This is literally only here to make Minecraft SHUT UP about non-signed messages while testing.
    // https://modrinth.com/mod/no-chat-reports/version/Fabric-1.21.3-v2.10.1
    modLocalRuntime("maven.modrinth:no-chat-reports:Cg7X9iDa")
}

repositories {
    maven(url = "https://maven.parchmentmc.org") {
        name = "ParchmentMC"
    }
    maven(url = "https://api.modrinth.com/maven") {
        name = "Modrinth"
        content {
            includeGroup("maven.modrinth")
        }
    }
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks {
    jar {
        from("LICENCE") {
            rename { "LICENSE_${project.extra["mod_name"]}"} // Use US spelling
        }
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(21)
    }
    processResources {
        filesMatching("fabric.mod.json") {
            expand(
                "mod_name" to project.extra["mod_name"],
                "mod_version" to project.version,
                "mod_description" to project.extra["mod_description"],
                "copyright_licence" to project.extra["copyright_licence"],

                "mod_home_url" to project.extra["mod_home_url"],
                "mod_source_url" to project.extra["mod_source_url"],
                "mod_issues_url" to project.extra["mod_issues_url"],

                "minecraft_version" to project.extra["minecraft_version"],
                "fabric_loader_version" to project.extra["fabric_loader_version"],
            )
        }
    }
    register<Delete>("cleanJar") {
        delete(fileTree("./dist") {
            include("*.jar")
        })
    }
    register<Copy>("copyJar") {
        dependsOn(getByName("cleanJar"))
        from(getByName("remapJar"))
        into("./dist")
        rename("(.*?)\\.jar", "\$1-fabric.jar")
    }
    build {
        dependsOn(getByName("copyJar"))
    }
}
