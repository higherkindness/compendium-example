package higherkindness.compendiumtest

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import higherkindness.compendiumtest.PersonProto._

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val person = Person("Timmy", "Thomas", 14)

    IO.delay(println(s"Hello ${person.firstName}, your age is ${person.age}")) *> IO.pure(ExitCode.Success)
  }
}
