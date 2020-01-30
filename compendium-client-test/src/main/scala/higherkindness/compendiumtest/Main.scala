package higherkindness.compendiumtest

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import cats.syntax._
import pureconfig.generic.auto._
import higherkindness.compendiumtest.config.Config
import io.chrisdavenport.log4cats.SelfAwareStructuredLogger
import io.chrisdavenport.log4cats.slf4j.Slf4jLogger
import org.apache.avro.Schema
import pureconfig.ConfigSource
import higherkindness.skeuomorph.mu.Transform.transformAvro
import higherkindness.skeuomorph.mu.MuF
import higherkindness.skeuomorph.mu.codegen
import higherkindness.skeuomorph.avro.AvroF.fromAvro
import higherkindness.droste._
import higherkindness.droste.data._
import higherkindness.droste.data.Mu._
import cats.implicits._
import scala.meta._

//sbt "project client" run
object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {

    implicit val conf = ConfigSource.default.load[Config].toOption
    val logger: SelfAwareStructuredLogger[IO] = Slf4jLogger.getLogger

    def handleProto(raw: String) = {

      val avroSchema: Schema = new Schema.Parser().parse(raw)

      val toMuSchema: Schema => Mu[MuF] =
        scheme.hylo(transformAvro[Mu[MuF]].algebra, fromAvro)

      val printSchemaAsScala: Mu[MuF] => Either[String, String] =
        codegen.schema(_).map(_.syntax)

      (toMuSchema >>> printSchemaAsScala)(avroSchema)
    }

    for {
      _ <- logger.info("Current version configuration")
      _ <- logger.info("\t" + conf)
      cliProto <- Client[IO]
        .retrieveProtocol("client", conf.map(_.schemaVersion.client))
      _ <- logger.debug("Retrieved: " + cliProto.map(_.raw))
      _ <- logger.debug(
        "Retrieved modified : " + cliProto.map(r => handleProto(r.raw))
      )

    } yield (ExitCode.Success)

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
