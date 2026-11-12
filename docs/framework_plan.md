# Framework plan (locked design)

## Layers

- **core** — `Config`, `BaseApiClient` (auth inside), `BaseApiAssert`
- **app** — `clients` (TodosClient), `models` (Todo), `flows` (TodoFlow)
- **test** — `api/comp`, `api/e2e`, `api/listeners` (skeleton)

## Decisions

- SUT v1: https://apichallenges.com
- Envs: local (default/QA), staging, prod via `-Denv=`
- Auth in BaseApiClient: X-CHALLENGER now; Bearer JWT later
- No Allure wiring in v1; report location open
- No ChallengerClient yet
- Package `assertions` (not `assert` — Java keyword)
