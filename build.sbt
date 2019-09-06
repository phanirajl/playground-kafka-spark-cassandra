name := "playground-kafka-spark-cassandra"

lazy val lateEvents = project
  .in(file("late-events"))
  .settings(
    name := "late-events"
  )
  .settings(commonSettings)
  .settings(protobufSettings)
  .settings(
    libraryDependencies ++= library.testLibs,
    libraryDependencies ++= library.configLibs
  )

lazy val library = new {
  object Version {
    val PureConfig     = "0.11.1"
    val Scala          = "2.11.12"
    val ScalaTest      = "3.0.1"
    val TypesafeConfig = "1.3.5-RC1"
  }

  import Version._
  val pureConfig     = "com.github.pureconfig" %% "pureconfig" % PureConfig
  val scalaTest      = "org.scalatest"         %% "scalatest"  % ScalaTest % "test"
  val typesafeConfig = "com.typesafe"          % "config"      % TypesafeConfig

  val testLibs = Seq(
    scalaTest
  )

  val configLibs = Seq(
    pureConfig,
    typesafeConfig
  )
}

// *****************************************************************************
// Aliases
// *****************************************************************************

addCommandAlias(
  "styleCheck",
  "; scalafmt::test ; test:scalafmt::test ; scalastyle ; test:scalastyle"
)

// -----------------------------------------------------------------------------
// common settings
// -----------------------------------------------------------------------------

lazy val commonSettings = Seq(
  scalaVersion := library.Version.Scala,
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    "-language:_",
    "-target:jvm-1.8",
    "-encoding",
    "UTF-8",
    "-feature",
    "-Xfatal-warnings"
  )
)

// -----------------------------------------------------------------------------
// protobuf/protoc compiler scala plugin
// -----------------------------------------------------------------------------

lazy val protobufSettings =
  Seq(
    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value
    )
  )
