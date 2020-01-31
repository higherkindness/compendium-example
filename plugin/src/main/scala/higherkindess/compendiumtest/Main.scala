package higherkindess.compendiumtest

import cats.effect.{ExitCode, IO, IOApp}
import higherkindness.compendiumtest.Product
import io.chrisdavenport.log4cats.SelfAwareStructuredLogger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import Decoder._

object Main extends IOApp with Reader[Product] {

  implicit val logger: SelfAwareStructuredLogger[IO] = Slf4jLogger.getLogger

  override def run(args: List[String]): IO[ExitCode] = {

    for {
      _ <- logger.info("Start reading the csv files")
      prods <- read[IO]("/product.csv")
      _ <- logger.info("Product first value:" + prods.headOption)
      suppls <- read[IO]("/supplier.csv")
      _ <- logger.info("Supplier first value:" + suppls.headOption)
      _ <- logger.error("Bye!")
    } yield ExitCode.Success
  }
}
