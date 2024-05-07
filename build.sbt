ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "spellify",
    idePackagePrefix := Some("fr.iai.spellify")
  )

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.18" % Test
)