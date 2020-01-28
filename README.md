##  Keycloak Custom Protocol Mapper with Kotlin and Gradle
This repository contains an example <a href="https://github.com/keycloak/keycloak">Keycloak</a> Protocol Mapper to customize the JWT returned by Keycloak
using Kotlin and Gradle.

This mapper enhances the JWT claim returned by the `http://{instance}/auth/realms/{realm}/protocol/openid-connect/token` endpoint so that it contains a mapping from a user's group memberships to the respective group roles.

## Building
- Execute the gradle task `shadowJar` to build a fat jar.
- Place the build artifact into the `/standalone/deployments` directory of your keycloak installation.
