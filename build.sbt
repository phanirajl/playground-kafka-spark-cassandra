name := "playground-kafka-spark-cassandra"

lazy val lateEvents = project
  .in(file("late-events"))
  .settings(
    name := "late-events"
  )
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= library.testLibraries
  )

lazy val library = new {
  object Version {
    val Scala     = "2.11.12"
    val ScalaTest = "3.0.1"
  }

  import Version._
  val scalatest = "org.scalatest" %% "scalatest" % ScalaTest % "test"

  val testLibraries = Seq(
    scalatest
  )
}

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
