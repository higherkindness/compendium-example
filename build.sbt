import cats.effect.IO
import higherkindness.compendium.models.IdlName
import sbt.Keys.version
import sbtcompendium.CompendiumUtils

lazy val version = new {
  val compendiumVersion = "0.0.1-SNAPSHOT"
  val cats: String = "2.1.0"
  val catseffect: String = "2.0.0"
  val log4cats = "0.3.0"
  val logbackClassic = "1.2.3"
  val hammock: String = "0.10.0"
  val pureConfig: String = "0.12.2"
}
lazy val logSettings: Seq[Def.Setting[_]] = Seq(
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % version.logbackClassic,
    "io.chrisdavenport" %% "log4cats-core" % version.log4cats,
    "io.chrisdavenport" %% "log4cats-slf4j" % version.log4cats
  )
)

lazy val configSettings = Seq(
  libraryDependencies ++= Seq(
    "com.github.pureconfig" %% "pureconfig" % version.pureConfig
  )
)

lazy val catsSettings = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % version.cats,
    "org.typelevel" %% "cats-effect" % version.catseffect
  )
)

lazy val compendiumClientSettings = Seq(
  libraryDependencies ++= Seq(
    "io.higherkindness" %% "compendium-client" % version.compendiumVersion,
    "com.pepegar" %% "hammock-core" % version.hammock,
    "com.pepegar" %% "hammock-asynchttpclient" % version.hammock,
    "com.pepegar" %% "hammock-apache-http" % version.hammock,
    "com.julianpeeters"               %% "avrohugger-core" % "1.0.0-RC22"
  )
)

lazy val pluginExampleSettings = Seq(
    libraryDependencies += "com.nrinaudo" %% "kantan.csv" % "0.6.0"
)


lazy val avroExample: Project = project
  .enablePlugins(CompendiumPlugin)
  .in(file("avroExample"))
  .settings(logSettings)
  .settings(pluginExampleSettings)
  .settings(catsSettings)
  .settings(
    compendiumProtocolIdentifiers := List("supplier","material","sale"),
    compendiumServerHost := "localhost",
    compendiumServerPort := 8080,
    compendiumFormatSchema:="avro",
    sourceGenerators in Compile += Def.task {
      compendiumGenClients.value
    }.taskValue
  )
  .settings(
    organization := "higherkindness",
    name := "compendium-test",
    scalaVersion := "2.12.10",
      scalacOptions ++= Seq(
          "-deprecation",
          "-encoding", "UTF-8",
          "-language:higherKinds",
          "-language:postfixOps",
          "-feature",
          "-Ypartial-unification",
          "-Xfatal-warnings",
      )
  )
