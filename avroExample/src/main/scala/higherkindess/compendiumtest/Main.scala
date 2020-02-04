package higherkindess.compendiumtest

import cats.effect.{ExitCode, IO, IOApp}
import higherkindness.compendiumtest._
import io.chrisdavenport.log4cats.SelfAwareStructuredLogger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import Decoder._

object Main extends IOApp with Reader[Product] {

  implicit val logger: SelfAwareStructuredLogger[IO] =
    Slf4jLogger.getLogger

  override def run(args: List[String]): IO[ExitCode] = {

    for {
      _ <- logger.info(
        "Start reading the csv files. Data automatically generated by Mockaroo."
      )
      prods <- read[Product, IO]("/product.csv")
      _ <- logger.info("Product first value:" + prods.headOption)
      suppls <- read[Supplier, IO]("/supplier.csv")
      _ <- logger.info("Supplier first value:" + suppls.headOption)
      mats <- read[Material, IO]("/material.csv")
      _ <- logger.info("Material first value:" + mats.headOption)
      sales <- read[Sale, IO]("/sale.csv")
      _ <- logger.info("Sale first value:" + sales.headOption)
      _ <- logger.info("Bye!")
    } yield ExitCode.Success
  }
}
