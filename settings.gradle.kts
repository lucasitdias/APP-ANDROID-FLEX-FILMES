// Gerenciamento de plugins
pluginManagement {

    // Repositórios de plugins
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

// Plugins globais
plugins {

    // Resolver toolchain Java
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

// Gerenciamento de dependências
dependencyResolutionManagement {

    // Bloqueia repositórios no projeto
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    // Repositórios
    repositories {
        google()
        mavenCentral()
    }
}

// Nome do projeto
rootProject.name = "FlexFilmes"

// Módulos
include(":app")