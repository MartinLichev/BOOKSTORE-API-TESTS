# Bookstore API Automation (Java + Rest-Assured + TestNG)

End-to-end API tests for **FakeRESTApi.Web V1** — Books (core), Authors, Activities, CoverPhotos, and Users.
This add-on pack includes **extra edge-case tests**, a **macOS-safe Docker Jenkinsfile**,
and an **expanded README** with Jenkins setup instructions (Docker Desktop, Allure).

> This project targets the assessment requirements: structure, happy+edge cases, reports,
> and CI integration. All tests hit the real public demo API at `https://fakerestapi.azurewebsites.net`.

---

## Quick Start (Local)

**Prereqs**

- JDK **17+**
- Maven **3.9+**
- (Optional) Allure locally: `brew install allure`

**Run**

```bash
mvn -q -ntp -B clean test
```

**Config** (`src/test/resources/config.properties`):

```properties
baseUrl=https://fakerestapi.azurewebsites.net
apiBasePath=/api/v1
useVersionedMediaType=false
versionedMediaType=application/json; v=1.0
logOnFailure=true
```

- Flip `useVersionedMediaType=true` only if the API requires `application/json; v=1.0`.

**Reports**

- Surefire XML/HTML: `target/surefire-reports`
- Allure:
  - Serve locally: `mvn allure:serve`
  - Generate static: `mvn allure:report`
  - Raw: `target/allure-results`

---

## CI: Jenkins (macOS + Docker Desktop)

This Jenkinsfile runs tests **inside a Docker container** (`maven:3.9.9-eclipse-temurin-17`)
so you don't need to install JDK/Maven on the Jenkins node.

### One-time Jenkins host setup (macOS)

1. Install and start **Docker Desktop** (whale icon must say _running_).
2. **Manage Jenkins → System**
   - **Shell executable** = `/bin/sh` (Alpine containers don't ship bash)
   - **Environment variables**:  
     `PATH+USRLOCAL = /usr/local/bin`  
     `PATH+HOMEBREW = /opt/homebrew/bin`
3. **Manage Jenkins → Plugins**: install
   - _Docker Pipeline_, _AnsiColor_, _Timestamper_
   - _Allure Jenkins Plugin_ (optional but recommended)
4. **Manage Jenkins → Tools**
   - **Allure Commandline**: add `allure` (Install automatically)
   - **Docker**: add a tool named **DockerCLI** with **Installation root** = `/usr/local`
     (because `docker` is at `/usr/local/bin/docker` on macOS)
5. **Restart Jenkins** (after plugin/tool changes).

### Create the job

- New Item → **Pipeline** → Pipeline script from SCM → point to this repo → `Jenkinsfile`.
- Build Now.

**What you get**

- Builds run in Docker (`maven:3.9.9-eclipse-temurin-17`).
- JUnit + Allure results archived; **Allure Report** link appears if the plugin is configured.

---

## Test Suites & Groups

All tests are **TestNG**. You can run subsets with groups:

```bash
mvn -q -ntp -B -Dgroups=smoke test
mvn -q -ntp -B -Dgroups=regression test
```

The default `testng.xml` includes both core and edge tests. You can adjust suites as needed.

---

## GitHub Actions (optional)

A ready workflow `.github/workflows/maven.yml` can run the same `mvn clean test` on pushes/PRs.

---

## Troubleshooting

- **Cannot run program "docker"** → Ensure Jenkins PATH includes `/usr/local/bin` and restart Jenkins.
- **/bin/bash not found** → Set Jenkins **Shell executable** to `/bin/sh`.
- **No Allure link** → Install _Allure Jenkins Plugin_ and add Allure Commandline in _Tools_.

---
