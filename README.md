# API Automation

Java + Maven + Rest Assured + TestNG framework targeting [API Challenges](https://apichallenges.com).

## Layout

```text
src/main/java/com/api/automation/
├── core/
│   ├── config/Config.java
│   ├── http/BaseApiClient.java      # HTTP + auth headers
│   └── assertions/BaseApiAssert.java
└── app/
    ├── clients/TodosClient.java
    ├── models/Todo.java
    └── flows/TodoFlow.java

src/test/java/com/api/automation/api/
├── BaseApiTest.java
├── comp/           # component tests
├── e2e/            # end-to-end flows
└── listeners/      # skeleton (not wired)
```

Note: package `assertions` is used because `assert` is a Java keyword.

## Requirements

- Java 11+
- Maven 3.9+

## Run

```bash
cd "/Users/barakshamir/Desktop/int/api-automation"
mvn clean test
mvn clean test -Denv=local
mvn clean test -Denv=staging
```

Default env is `local` (QA). Config files: `src/test/resources/config/*.properties`.

## Later (not in v1)

- Allure reporting (location still open)
- Contract package, Pact, microservices
- Real TestNG listeners, parallel, negative/perf suites
