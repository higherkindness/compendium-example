package higherkindness.compendiumtest.config

import higherkindness.compendium.models.config.CompendiumClientConfig

case class Config(httpClientConfig: CompendiumClientConfig,
                  schemaVersion: Version)
case class Version(client: Int, supplier: Int, material: Int, product: Int)
