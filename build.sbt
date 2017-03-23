organization in ThisBuild := "ua.gov.testportal"
version in ThisBuild := "0.1"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

lazy val `external-testing` = (project in file("."))
  .aggregate(`external-testing-api`, `external-testing-impl`, `external-testing-stream-api`, `external-testing-stream-impl`)

lazy val `external-testing-api` = (project in file("external-testing-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `external-testing-impl` = (project in file("external-testing-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`external-testing-api`)

lazy val `external-testing-stream-api` = (project in file("external-testing-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `external-testing-stream-impl` = (project in file("external-testing-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`external-testing-stream-api`, `external-testing-api`)

