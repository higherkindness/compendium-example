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

- sbt plugin

    Add to your `project/plugin.sbt` file the following line

    `addSbtPlugin("io.higherkindness" %% "sbt-compendium" % "0.0.1-SNAPSHOT")`

    Also to your `build.sbt` file add to your project settings

    `.settings(
        compendiumProtocolIdentifiers := List("product"),
        compendiumServerHost := "localhost",
        compendiumServerPort := 8080,
        compendiumFormatSchema:="avro",
        sourceGenerators in Compile += Def.task {
          compendiumGenClients.value
        }.tapendiumServerPort := 8080,
        compendiumFormatSchema:="proto",
        sourceGenerators in Compile += Def.task {
          compendiumGenClients.value
        }.taskValue
    )`

    The configuration works as follow:

    - compendiumServerHost: String. Url of the compendium server. Default: "localhost"
    - compendiumServerPort: Integer. Port of the compendium server. Default: 47047
    - compendiumFormatSchema: String. Schema type to download. Default: "avro". Valid values: "avro", "proto".
    - compendiumProtocolIdentifiers: `List[String]`. Protocol identifiers to be retrieved from compendium server. Default: Nil


## Model example


Client:

    {
      "type": "record",
      "name": "Client",
      "namespace": "higherkindness.compendiumtest",
      "fields": [
        {
            "name": "id_client",
            "type": "string"
        },  
        {
            "name": "name",
            "type": "string"
        },
        {
            "name": "surname",
            "type": "string"
        },
        {
            "name": "email",
            "type": "string"
        }
      ]
    }

Supplier:

    {
      "type": "record",
      "name": "Supplier",
      "namespace": "higherkindness.compendiumtest",
      "fields": [
        {
            "name": "id_supplier",
            "type": "string"
        }, 
        {
            "name": "name",
            "type": "string"
        },
        {
            "name": "email",
            "type": "string"
        },
        {
            "name": "phone",
            "type": "string"
        }
      ]
    }


Product #1:

    {
      "type": "record",
      "name": "Product",
      "namespace": "higherkindness.compendiumtest",
      "fields": [
        {
            "name": "id_prod",
            "type": "string"
        },
        {
            "name": "description",
            "type": "string"
        },    
        {
            "name": "color",
            "type": "string"
        },
        {
            "name": "size",
            "type": "string"
        }
      ]
    }


Product #2:

    {
      "type": "record",
      "name": "Product",
      "namespace": "higherkindness.compendiumtest",
      "fields": [
        {
            "name": "name",
            "type": "string"
        },
        {
            "name": "color",
            "type": "string"
        },
        {
            "name": "size",
            "type": "string"
        },
        {
            "name": "soldDate",
            "type": "string"
        }
      ]
    }

Sale:

    {
      "type": "record",
      "name": "Sale",
      "namespace": "higherkindness.compendiumtest",
      "fields": [
        {
            "name": "client",
            "type": "higherkindness.compendiumtest.Client"
        },
        {
            "name": "product",
            "type": "higherkindness.compendiumtest.Product"
        }
      ]
    }

Material #1:

    {
      "type": "record",
      "name": "Material",
      "namespace": "higherkindness.compendiumtest",
      "fields": [
        {
            "name": "name",
            "type": "string"
        },
        {
            "name": "code",
            "type": "string"
        },
        {
            "name": "shipId",
            "type": "string"
        }
      ]
    }

Material #2:


    {
      "type": "record",
      "name": "Material",
      "namespace": "higherkindness.compendiumtest",
      "fields": [
        {
            "name": "name",
            "type": "string"
        },
        {
            "name": "code",
            "type": "string"
        },
        {
            "name": "originIdentifier",
            "type": "higherkindness.compendiumtest.Supplier"
        },    
        {
            "name": "shipId",
            "type": "string"
        }
      ]
    }

Process:

    {
      "type": "record",
      "name": "Process",
      "namespace": "higherkindness.compendiumtest",
      "fields": [
        {
            "name": "name",
            "type": "string"
        },
        {
            "name": "mat",
            "type": {
                "type": "array",
                "items": "higherkindness.compendiumtest.Material"
            }
        },
        {
            "name": "prod",
            "type": "higherkindness.compendiumtest.Product"
        }
      ]
    }


[From json to string Converter](https://tools.knowledgewalls.com/jsontostring)