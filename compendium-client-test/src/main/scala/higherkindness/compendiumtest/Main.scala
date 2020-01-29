package higherkindness.compendiumtest

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import cats.syntax._
import higherkindness.compendium.models.Protocol
import io.chrisdavenport.log4cats.SelfAwareStructuredLogger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {

    //sbt "project client" "run 2"
    val logger: SelfAwareStructuredLogger[IO] = Slf4jLogger.getLogger

    for {
      _ <- logger.info("Current arguments configuration:")
      _ <- logger.info(
        "\t - first parameter: version of the schema, integer value"
      )
      version = args.headOption.map(_.toInt)
      ans <- Client[IO]()
        .retrieveProtocol("a", version)
      _ <- logger.info(s"Here you are version #${2}:\n" + ans)
      /*_ <- logger.info(
        s"Saving...\n message Contact {\n  string name = 1;\n  string surname = 2;\n}"
      )*/
      /* isStored <- Client[IO]().storeProtocol(
        "avro2",
        Protocol(
          "{\"namespace\":\"correct.avro\",\"type\":\"record\",\"name\":\"User\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"age\",\"type\":\"int\"},{\"name\":\"address\",\"type\":[\"string\",\"null\"]}]}"
        )
      )*/
      /*isStored <- Client[IO]().storeProtocol(
          "p",
          Protocol(
              "message Contact {\n  string name = 1;\n  string surname = 2;\n}"
          )
      )
      _ <- logger.info(s"Stored successfully: " + isStored)*/
    } yield
      ans.fold(ExitCode.Error)(r => {
        logger.info(s"Here you are version #${2}:\n" + r)
        ExitCode.Success
      })
  }
}
