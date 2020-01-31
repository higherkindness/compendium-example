package higherkindness.compendiumtest

import avrohugger.Generator
import avrohugger.format.Standard
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import cats.syntax._
import pureconfig.generic.auto._
import higherkindness.compendiumtest.config.Config
import io.chrisdavenport.log4cats.SelfAwareStructuredLogger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import pureconfig.ConfigSource

import cats.implicits._

//sbt "project client" run
object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {

    implicit val conf = ConfigSource.default.load[Config].toOption
    val logger: SelfAwareStructuredLogger[IO] = Slf4jLogger.getLogger

    def handleAvro(raw: String) =
      Generator(Standard).stringToFile(raw)

    for {
      _ <- logger.info("Current version configuration")
      _ <- logger.info("\t" + conf)
      cliProto <- Client[IO]
        .retrieveProtocol("product", conf.map(_.schemaVersion.product))
      _ <- logger.debug("Retrieved: " + cliProto.map(a => handleAvro(a.raw)))
      _ <- logger.debug("File created! Look on target/generated-sources")
    } yield ExitCode.Success

  }
}
/*   /* _ <- logger.info(
        s"Saving...\n message Contact {\n  string name = 1;\n  string surname = 2;\n}"
      )
      isStored <- Client[IO]().storeProtocol(
        "avro2",
        Protocol(
          """{\\"namespace\\":\\"correct.avro\\",\\"type\\":\\"record\\",\\"name\\":\\"User\\",\\"fields\\":[{\\"name\\":\\"name\\",\\"type\\":\\"string\\"},{\\"name\\":\\"age\\",\\"type\\":\\"int\\"},{\\"name\\":\\"address\\",\\"type\\":[\\"string\\",\\"null\\"]}]}"""
        )
      )*/
      /*isStored <- Client[IO]().storeProtocol(
          "p",
          Protocol(
              "message Contact {\n  string name = 1;\n  string surname = 2;\n}"
          )
      )
      _ <- logger.info(s"Stored successfully: " + isStored)*/*/
