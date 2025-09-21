# Bookstore API Automation (Java + Rest-Assured + TestNG)

End‑to‑end API test project for the **FakeRESTApi** Online Bookstore.  
It covers Books (core), plus Activities, Authors, CoverPhotos, and Users.  
Generates **Allure** and **Surefire** reports and includes **Jenkins** and **GitHub Actions** CI examples.

> API brief used for this project: *API Automation Testing Assessment: Online Bookstore* (Books are mandatory; Authors optional).

## Tech Stack
- Java 17, Maven
- Rest-Assured 5, TestNG
- Jackson (JSON), AssertJ
- Allure reporting (+ REST Assured filter)
- DataFaker (random test data)

## Run Locally

### Prereqs
- JDK 17+
- Maven 3.9+
- VS Code: **Extension Pack for Java**

### Quick start
```bash
mvn -q -ntp -B clean test
```

### Configuration
Edit `src/test/resources/config.properties`:
```properties
baseUrl=https://fakerestapi.azurewebsites.net
apiBasePath=/api/v1
useVersionedMediaType=false
versionedMediaType=application/json; v=1.0
logOnFailure=true
```

- If your API requires the versioned media type (`application/json; v=1.0`), set `useVersionedMediaType=true`.

### Reports
- **Surefire (XML/HTML)**: `target/surefire-reports`
- **Allure**:
  - Generate locally: `mvn allure:report`
  - Serve locally: `mvn allure:serve`
  - Raw results: `target/allure-results`

## CI/CD

### Jenkins
`Jenkinsfile` is included (Docker agent w/ JDK 17).

### GitHub Actions
Ready-to-use workflow at `.github/workflows/maven.yml`.

---

MIT © 2025