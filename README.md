# Bookstore API Automation (Java 17 + Rest-Assured + TestNG)

API test automation for **FakeRESTApi.Web V1**. The suite covers **Books** (core),
plus **Authors** (optional for bonus), **Activities**, **CoverPhotos**, and **Users**. It includes
**happy-path and edge cases**, **data‑driven tests**, **Allure & Surefire reporting**, and a
**Jenkins (Docker) pipeline** with **Maven profiles** for smoke/full/regression.

> (POM framework, happy+edge, reports, and CI/CD).

---

## Tech Stack

- Java **17**, Maven **3.9+**
- Rest‑Assured 5, TestNG 7
- Jackson (JSON), AssertJ
- Allure (report + RA filter)
- DataFaker for random test data
- Jenkinsfile (Dockerized), GitHub Actions workflow

---

## Project Structure (high‑level)

```
bookstore-api-tests/
  pom.xml
  Jenkinsfile                     # Dockerized pipeline; profile-selectable
  testng.xml                      # original suite (kept)
  testng-full.xml                 # profile: full (default)
  testng-smoke.xml                # profile: smoke
  testng-regression.xml           # profile: regression
  src/
    main/java/com/example/bookstore/api/
      client/                     # typed clients per resource
      http/RequestSpecFactory.java# base spec (baseUrl, basePath, headers, Allure filter)
      models/                     # POJOs
      utils/                      # helpers
    test/java/com/example/bookstore/api/
      BaseApiTest.java
      tests/
        ActivitiesApiTests.java   # original tests (kept)
        AuthorsApiTests.java
        BooksApiTests.java
        CoverPhotosApiTests.java
        UsersApiTests.java
        # New coverage (data-driven & edge):
        SmokeTests.java
        DataProviders.java
        BooksDataDrivenTests.java
        AuthorsDataDrivenTests.java
        BooksEdgeTests.java
        AuthorsEdgeTests.java
        ActivitiesEdgeTests.java
        CoverPhotosEdgeTests.java
        UsersEdgeTests.java
```

---

## Configuration

`src/test/resources/config.properties` (or system properties) control the base endpoint and headers:

```properties
baseUrl=https://fakerestapi.azurewebsites.net
apiBasePath=/api/v1
useVersionedMediaType=false
versionedMediaType=application/json; v=1.0
logOnFailure=true
```

- Flip `useVersionedMediaType=true` only if the API **requires** `application/json; v=1.0`.
- You can override any of these via `-Dname=value` on the Maven command line.

---

## Running Locally

### Prereqs

- JDK **17+**
- Maven **3.9+**
- (Optional) Allure CLI for local HTML report: `brew install allure`

### Quick start

```bash
mvn -q -ntp -B clean test
```

### Profiles & Suites

The project uses profiles that switch the **TestNG suite file** used by Surefire:

- `full` _(default)_ → runs **everything** (`testng-full.xml`)
- `smoke` → runs only group **smoke** (`testng-smoke.xml`)
- `regression` → runs only group **regression** (`testng-regression.xml`)

Examples:

```bash
# Full (default)
mvn -q -ntp -B clean test

# Smoke
mvn -q -ntp -B -P smoke clean test

# Regression
mvn -q -ntp -B -P regression clean test
```

### Data‑Driven Tests

`DataProviders` supply:

- `bookIds`: 1, 2, 10, 20 (used by `BooksDataDrivenTests#getBook_multipleIds_returns200`)
- `authorIds`: 1, 2, 5
- `idBooks`: 1, 2, 3 (for `/Authors/authors/books/{idBook}`)
- `acceptMatrix`: `application/json`, `*/*`, `application/json; v=1.0`, `text/plain; v=1.0`, `application/xml`

### Reports

- **Surefire (XML/HTML)** → `target/surefire-reports`
- **Allure**:
  - Serve locally: `mvn allure:serve`
  - Generate static site: `mvn allure:report`
  - Run tests + immediately open a static report: `mvn clean test allure:report && open target/site/allure-maven/index.html`
  - Raw results: `target/allure-results`

---

## Jenkins CI (macOS host + Docker Desktop)

This pipeline runs in **Docker** (`maven:3.9.9-eclipse-temurin-17`), so the Jenkins node doesn’t need JDK/Maven installed.

### One‑time host setup

1. Install & start **Docker Desktop** (whale icon “Running”).
2. **Manage Jenkins → System**
   - **Shell executable**: `/bin/sh` (Alpine containers don’t ship bash)
   - **Environment variables**:  
     `PATH+USRLOCAL = /usr/local/bin`  
     `PATH+HOMEBREW = /opt/homebrew/bin`
3. **Manage Jenkins → Plugins**: install
   - _Docker Pipeline_, _AnsiColor_, _Timestamper_
   - _Allure Jenkins Plugin_ (optional but recommended)
4. **Manage Jenkins → Tools**
   - **Docker**: add **DockerCLI** with **Installation root** = `/usr/local`  
     (because Docker is at `/usr/local/bin/docker` on macOS)
   - **Allure Commandline**: add `allure` (**Install automatically**)
5. Restart Jenkins after plugin/tool changes.

### Job setup

- **New Item → Pipeline → Pipeline script from SCM** → your repo → `Jenkinsfile`.
- Parameters exposed by the Jenkinsfile:
  - `MAVEN_PROFILE`: `full | smoke | regression`
  - `BASE_URL`, `API_BASE_PATH`
  - `USE_VERSIONED_MEDIA_TYPE`, `VERSIONED_MEDIA_TYPE`
- The pipeline publishes **JUnit** and **Allure** (if Allure plugin is configured).

---

## GitHub Actions (optional)

A ready workflow at `.github/workflows/maven.yml` runs `mvn clean test` with Java 17.  
Customize matrix or cron as needed.

---

## Troubleshooting

- **`docker: command not found` in Jenkins** → ensure PATH has `/usr/local/bin` via `PATH+USRLOCAL`, and Docker Desktop is running.
- **`/bin/bash: not found` in Docker agent** → set Jenkins **Shell executable** to `/bin/sh`.
- **No Allure report link** → install _Allure Jenkins Plugin_ and configure **Tools → Allure Commandline**.

---

## Project Summary

This project contains the following:

- **Clean structure & reusable code** (clients, request spec, base test)
- **Happy + edge cases** across Books and Authors (+ bonus resources)
- **Reports** (Surefire + Allure)
- **CI/CD** via Jenkinsfile and a GitHub Actions workflow
