val CompendiumVersion = "0.0.1-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    organization := "higherkindness",
    name := "compendium-test",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8",
    scalacOptions ++= Seq("-Ypartial-unification"),
    libraryDependencies ++= Seq(
      "higherkindness" %% "compendium-client" % CompendiumVersion,
    )
  )

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Ypartial-unification",
  "-Xfatal-warnings",
)
