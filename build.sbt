val CompendiumVersion = "0.0.1-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    organization := "higherkindness",
    name := "compendium-example",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8",
    scalacOptions ++= Seq("-Ypartial-unification"),
    libraryDependencies ++= Seq(
      "io.higherkindness" %% "compendium-client" % CompendiumVersion
    ),
    compendiumServerHost := "localhost",
    compendiumServerPort := 8080,
    compendiumProtocolIdentifiers := Seq("person"),
    sourceGenerators in Compile += Def.task {
      compendiumGenClients.value
    }.taskValue
  ).enablePlugins(CompendiumPlugin)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Ypartial-unification",
  "-Xfatal-warnings",
)
