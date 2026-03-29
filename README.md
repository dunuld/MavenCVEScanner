# Maven CVE Scanner

A Java-based CLI tool to scan Maven `pom.xml` files for vulnerable dependencies.

## Overview

`MavenCVEScanner` parses a provided `pom.xml` file, extracts its dependencies, and checks them against a database of known vulnerabilities (CVEs). 

> **Note:** Currently, the CVE check uses a mock implementation. Real API integration is planned.

## Requirements

- **Java**: 17 or higher
- **Maven**: 3.6+ (for building)

## Setup & Run

### 1. Build the Project
Use Maven to compile and package the application:
```bash
mvn package
```
This generates a JAR file in the `target/` directory: `cve-scanner-1.0-SNAPSHOT.jar`.

### 2. Run the Scanner
Execute the JAR and provide the path to the `pom.xml` you wish to scan:
```bash
java -jar target/cve-scanner-1.0-SNAPSHOT.jar /path/to/your/pom.xml
```

#### Example Usage:
```bash
java -jar target/cve-scanner-1.0-SNAPSHOT.jar pom.xml
```

## Scripts & Commands

- `mvn clean`: Removes build artifacts.
- `mvn package`: Compiles code and packages it into a runnable JAR.
- `mvn test`: (TODO) Run unit tests once implemented.

## Project Structure

```text
.
├── pom.xml                 # Maven configuration and dependencies
├── src
│   └── main
│       └── java
│           └── com
│               └── example
│                   ├── Main.java         # CLI entry point (picocli)
│                   ├── PomParser.java    # XML parsing for dependencies
│                   ├── Dependency.java   # Data model for a dependency
│                   └── CveService.java   # Vulnerability check logic (Mock)
└── README.md
```

## Environment Variables

- None currently required.

## Tests

- **TODO:** Implement unit tests for `PomParser` and `CveService`.
- To run tests (when available): `mvn test`.

## License

- **TODO:** Specify license (e.g., MIT, Apache 2.0).

---
*Created by Junie.*
