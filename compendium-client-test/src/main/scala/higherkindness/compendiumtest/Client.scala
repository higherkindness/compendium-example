package higherkindness.compendiumtest

import cats.effect.Sync
import hammock.apache.ApacheInterpreter
import higherkindness.compendium.CompendiumClient
import higherkindness.compendium.models.config.{
  CompendiumClientConfig,
  HttpConfig
}
import pureconfig.ConfigSource
import pureconfig.generic.auto._

object Client {

  val config = ConfigSource.default.load[CompendiumClientConfig].toOption

  def apply[F[_]: Sync](): CompendiumClient[F] = {
    implicit val compendiumClient =
      config.getOrElse(CompendiumClientConfig(HttpConfig("localhost", 8080)))
    implicit val interpreter = ApacheInterpreter.instance[F]
    CompendiumClient[F]
  }
}
