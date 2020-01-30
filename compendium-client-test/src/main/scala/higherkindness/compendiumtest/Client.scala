package higherkindness.compendiumtest

import cats.effect.Sync
import hammock.apache.ApacheInterpreter
import higherkindness.compendium.CompendiumClient
import higherkindness.compendium.models.config.{
  CompendiumClientConfig,
  HttpConfig
}
import higherkindness.compendiumtest.config.Config

object Client {

  def apply[F[_]: Sync]()(
    implicit config: Option[Config]
  ): CompendiumClient[F] = {
    implicit val conf = config
      .map(_.httpClientConfig)
      .getOrElse(CompendiumClientConfig(HttpConfig("localhost", 8080)))
    implicit val interpreter = ApacheInterpreter.instance[F]
    CompendiumClient[F]()
  }
}
