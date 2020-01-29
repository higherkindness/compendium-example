import sbt.addCompilerPlugin

lazy val version = new {
    val compendiumVersion = "0.0.1-SNAPSHOT"
    val cats: String             = "2.1.0"
    val catseffect: String             = "2.0.0"
    val log4cats = "0.3.0"
    val logbackClassic = "1.2.3"
    val hammock: String          = "0.10.0"
    val pureConfig: String = "0.12.2"
}
lazy val logSettings: Seq[Def.Setting[_]] = Seq(
    libraryDependencies ++= Seq(
        "ch.qos.logback" % "logback-classic" % version.logbackClassic,
        "io.chrisdavenport" %% "log4cats-core" % version.log4cats,
        "io.chrisdavenport" %% "log4cats-slf4j" % version.log4cats
    )
)

lazy val configSettings = Seq(libraryDependencies ++= Seq(
    "com.github.pureconfig" %% "pureconfig" % version.pureConfig
))

lazy val catsSettings = Seq(libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % version.cats,
    "org.typelevel" %% "cats-effect" % version.catseffect))

lazy val compendiumClientSettings = Seq(libraryDependencies ++= Seq(
    "io.higherkindness" %% "compendium-client" % version.compendiumVersion,
    "com.pepegar" %% "hammock-core"            % version.hammock,
    "com.pepegar" %% "hammock-apache-http" % version.hammock))

lazy val compendiumServerSettings = Seq(libraryDependencies ++= Seq(
    "io.higherkindness" %% "compendium-common" % version.compendiumVersion
    //"io.higherkindness" %% "compendium-server" % version.compendiumVersion
    // addSbtPlugin("io.higherkindness" %% "sbt-compendium" % version.compendiumVersion)
))

lazy val root = (project in file("."))
  .settings(
    organization := "higherkindness",
    name := "compendium-test", //version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.10",
    scalacOptions ++= Seq("-Ypartial-unification")
  )

lazy val client: Project = project
        .in(file("compendium-client-test"))
                .settings(logSettings)
                .settings(configSettings)
                .settings(catsSettings)
                .settings(compendiumClientSettings)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Ypartial-unification",
  "-Xfatal-warnings",
)
