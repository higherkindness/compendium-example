import cats.effect.{ExitCode, IO, IOApp}
import compendium.models.Product
import io.chrisdavenport.log4cats.SelfAwareStructuredLogger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    implicit val logger: SelfAwareStructuredLogger[IO] =
      Slf4jLogger.getLogger

    for {
      _ <- logger.info("Start proccess.")
      _ = Product("", "t-shirt", "", "")
    } yield ExitCode.Success
  }
}
