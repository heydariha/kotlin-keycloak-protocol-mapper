##  Keycloak Custom Protocol Mapper with Kotlin and Gradle
This repository contains an example Keycloak Protocol Mapper to customize the JWT returned by Keycloak
using Kotlin and Gradle.

## Building
- Execute the gradle task `shadowJar` to build a fat jar.
- Place the build artifact into the `/standalone/deployments` directory of your keycloak installation.
