# COMPENDIUM EXAMPLE

## Testing on local device

**Required:**

1. postgres or docker

2. postman or similar

**Set up configuration:**

- Postgres in local.

    By default: pointing to `localhost:5432` and database `postgres`.
 Configurable on environment var: `COMPENDIUM_METADATA_STORAGE_JDBC_URL`

- Docker



- Postman

    Import collection on `src/main/resources/compendium.postman_collection.json`. Please notice this has `localhost:8080` as a default host for compendium server.
    POST calls has to give a **string** with the whole schema. It wil be parsed internally.

- Client library

    [Interpreter](http://pepegar.com/hammock/interpreters.html) needed.
    Client configuration needed `CompendiumClientConfig` need to be specified with host and port to sent the requests. By default server has `localhost:8080`.




