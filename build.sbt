ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "spellify",
    idePackagePrefix := Some("fr.iai.spellify")
  )

val circeVersion = "0.14.7"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio" % "2.0.22",
  "dev.zio" %% "zio-http" % "3.0.0-RC6",
  "dev.zio" %% "zio-json" % "0.6.2",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "org.scalatest" %% "scalatest" % "3.2.18" % Test
)