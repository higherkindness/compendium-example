import higherkindness.compendium.models.IdlName
import sbtcompendium.ProtocolAndVersion

lazy val version = new {
  val cats: String = "2.1.0"
  val catseffect: String = "2.0.0"
  val log4cats = "0.3.0"
  val logbackClassic = "1.2.3"
  val hammock: String = "0.10.0"
  val pureConfig: String = "0.12.2"
  val avroHugger: String = "1.0.0-RC22"
  val kantan: String = "0.6.0"
    val pbdirect: String            = "0.4.1"
    val fs2 = "1.0.0"
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

lazy val pluginExampleSettings = Seq(
    libraryDependencies += "com.nrinaudo" %% "kantan.csv" % version.kantan
)

lazy val avroExample: Project = project
  .in(file("avroExample"))
  .settings(logSettings)
  .settings(pluginExampleSettings)
  .settings(catsSettings)
  .settings(
    compendiumSrcGenProtocolIdentifiers := List(ProtocolAndVersion("supplier",None),ProtocolAndVersion("material",None),ProtocolAndVersion("sale",None)),
    compendiumSrcGenServerHost := "localhost",
    compendiumSrcGenServerPort := 8080,
    sourceGenerators in Compile += Def.task {
      compendiumSrcGenClients.value
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

lazy val protoExample: Project = project
        .in(file("protoExample"))
        .settings(logSettings)
        .settings(pluginExampleSettings)
        .settings(catsSettings)
        .settings(libraryDependencies ++=Seq(
            "com.47deg"    %% "pbdirect"   % version.pbdirect,
            "co.fs2" %% "fs2-core" % version.fs2
        ))
        .settings(
            compendiumSrcGenProtocolIdentifiers := List(ProtocolAndVersion("example",None)),
            compendiumSrcGenServerHost := "localhost",
            compendiumSrcGenServerPort := 8080,
            compendiumSrcGenFormatSchema := IdlName.Protobuf,
            sourceGenerators in Compile += Def.task {
                compendiumSrcGenClients.value
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


