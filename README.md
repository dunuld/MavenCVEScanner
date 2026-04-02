# Maven CVE Scanner

A Java-based CLI tool to scan Maven `pom.xml` files for vulnerable dependencies by querying the NVD (National Vulnerability Database) API.

## Overview

`MavenCVEScanner` parses a provided `pom.xml` file, extracts its dependencies (groupId, artifactId, version), and checks the artifact names against known vulnerabilities (CVEs) using the NVD API 2.0.

Key features:
- Scans `pom.xml` for dependencies.
- Integrates with NVD API via HTTP.
- CLI interface powered by [picocli](https://picocli.info/).
- Logging support via SLF4J and Logback.

## Requirements

- **Java**: 17 or higher
- **Maven**: 3.6+ (for building)
- **Internet Access**: Required for querying the NVD API.

## Setup & Run

### 1. Build the Project
Use Maven to compile and package the application:
```bash
mvn clean package
```
This generates an executable JAR file in the `target/` directory: `cve-scanner-1.0-SNAPSHOT.jar`.

### 2. Run the Scanner
Execute the JAR and provide the path to the `pom.xml` you wish to scan:
```bash
java -jar target/cve-scanner-1.0-SNAPSHOT.jar <path-to-pom.xml>
```

#### Example Usage:
```bash
java -jar target/cve-scanner-1.0-SNAPSHOT.jar pom.xml
```

## Scripts & Commands

- `mvn clean`: Removes build artifacts in the `target/` directory.
- `mvn compile`: Compiles the source code.
- `mvn package`: Runs tests and packages the application into a JAR.
- `mvn test`: Executes unit tests using JUnit 5 and Mockito.

## Environment Variables

- **TODO**: Add support for NVD API Key (e.g., `NVD_API_KEY`) to avoid rate limiting. Currently, the tool uses the public API without a key.

## Tests

The project includes unit tests for core components:
- `PomParserImplTest`: Verifies XML parsing of `pom.xml`.
- `CveServiceImplTest`: Tests NVD API integration (mocked).
- `DependencyTest`: Tests the dependency data model.

To run all tests:
```bash
mvn test
```

## Project Structure

```text
.
├── pom.xml                        # Maven configuration and dependencies
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── cvescanner
│   │   │           ├── Main.java          # CLI entry point (picocli)
│   │   │           ├── data
│   │   │           │   └── Dependency.java # Data model for a dependency
│   │   │           ├── http
│   │   │           │   ├── HttpResponseData.java
│   │   │           │   └── IHttpProvider.java
│   │   │           ├── pomparser
│   │   │           │   ├── IPomParser.java
│   │   │           │   └── PomParserImpl.java # XML parsing for dependencies
│   │   │           └── service
│   │   │               ├── ICveService.java
│   │   │               └── CveServiceImpl.java # NVD API integration logic
│   │   └── resources
│   │       └── logback.xml        # Logging configuration
│   └── test                       # Unit tests
└── README.md
```

## License

- **TODO:** Specify license (e.g., MIT, Apache 2.0).

---
*Updated on 2026-04-02.*
