name := "playground-kafka-spark-cassandra"

import sbt.Keys._

lazy val protobufs = project
  .in(file("protobufs"))
  .settings(
    name := "protobufs"
  )
  .settings(
    libraryDependencies ++= Seq(
      library.scalapbRuntime
    )
  )
  .settings(commonSettings)
  .settings(protobufSettings)

lazy val lateEvents = project
  .in(file("late-events"))
  .settings(
    name := "late-events"
  )
  .settings(commonSettings)
  .settings(protobufSettings)
  .settings(assemblySettings)
  .settings(
    libraryDependencies ++= library.configLibs,
    libraryDependencies ++= library.logLibs,
    libraryDependencies ++= library.sparkLibs,
    libraryDependencies ++= library.testLibs
  )
  .dependsOn(protobufs)

lazy val topicReader = project
  .in(file("topic-reader"))
  .settings(
    name := "topic-reader"
  )
  .settings(commonSettings)
  .settings(protobufSettings)
  .settings(assemblySettings)
  .settings(
    libraryDependencies ++= library.configLibs,
    libraryDependencies ++= library.kafkaLibs,
    libraryDependencies ++= library.logLibs,
    libraryDependencies ++= library.testLibs
  )
  .dependsOn(protobufs)

lazy val generator = project
  .in(file("generator"))
  .settings(
    name := "generator"
  )
  .settings(commonSettings)
  .settings(protobufSettings)
  .settings(assemblySettings)
  .settings(
    libraryDependencies ++= library.configLibs,
    libraryDependencies ++= library.kafkaLibs,
    libraryDependencies ++= library.logLibs,
    libraryDependencies ++= library.testLibs
  )
  .dependsOn(protobufs)

lazy val library = new {
  object Version {
    val Disruptor      = "3.4.2"
    val Kafka          = "2.3.0"
    val Log4j          = "2.12.1"
    val PureConfig     = "0.11.1"
    val Scala          = "2.11.12"
    val ScalaTest      = "3.0.1"
    val Spark          = "2.4.0"
    val TypesafeConfig = "1.3.5-RC1"
  }

  import Version._
  val disruptor        = "com.lmax"                 % "disruptor"                   % Disruptor
  val kafka            = "org.apache.kafka"         %% "kafka"                      % Kafka
  val log4j            = "org.apache.logging.log4j" % "log4j-api"                   % Log4j
  val pureConfig       = "com.github.pureconfig"    %% "pureconfig"                 % PureConfig
  val scalapbRuntime   = "com.thesamet.scalapb"     %% "scalapb-runtime"            % scalapb.compiler.Version.scalapbVersion % "protobuf"
  val scalaTest        = "org.scalatest"            %% "scalatest"                  % ScalaTest % "test"
  val slf4jBridge      = "org.apache.logging.log4j" % "log4j-slf4j-impl"            % Log4j
  val sparkCore        = "org.apache.spark"         % "spark-core_2.11"             % Spark % "provided"
  val sparkSql         = "org.apache.spark"         % "spark-sql_2.11"              % Spark % "provided"
  val sparkStreaming   = "org.apache.spark"         % "spark-streaming_2.11"        % Spark % "provided"
  val sparkKafkaBridge = "org.apache.spark"         %% "spark-streaming-kafka-0-10" % Spark
  val typesafeConfig   = "com.typesafe"             % "config"                      % TypesafeConfig

  val configLibs = Seq(
    pureConfig,
    typesafeConfig
  )

  val kafkaLibs = Seq(
    kafka
  )

  val logLibs = Seq(
    disruptor,
    log4j,
    slf4jBridge
  )

  val sparkLibs = Seq(
    sparkCore,
    sparkSql,
    sparkStreaming,
    sparkKafkaBridge,
  )

  val testLibs = Seq(
    scalaTest
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
  ),
  unmanagedSourceDirectories.in(Compile) := Seq(scalaSource.in(Compile).value),
  unmanagedSourceDirectories.in(Test) := Seq(scalaSource.in(Test).value)
)

// -----------------------------------------------------------------------------
// protobuf/protoc compiler scala plugin
// -----------------------------------------------------------------------------

lazy val protobufSettings =
  Seq(
    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value
    ),
    // add src/main/protobuf directory to be included in artifact jar
    unmanagedResourceDirectories in Compile ++= (PB.protoSources in Compile).value
  )

// -----------------------------------------------------------------------------
// sbt assembly plugin settings
// -----------------------------------------------------------------------------

lazy val assemblySettings = {
  assemblyShadeRules in (IntegrationTest, assembly) ++= Seq(
    // https://scalapb.github.io/sparksql.html
    ShadeRule.rename("com.google.protobuf.**" -> "shadeproto.@1").inAll
  )

  // https://stackoverflow.com/questions/41303037/why-does-spark-application-fail-with-classnotfoundexception-failed-to-find-dat
  assemblyMergeStrategy in assembly := {
    case "META-INF/services/org.apache.spark.sql.sources.DataSourceRegister" => MergeStrategy.concat
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
  }
}
