import sbt.*

object Dependencies {
  private val awsSdkVersion = "2.31.11"
  private val scalaLogging = "3.9.5"
  private val testContainersVersion = "0.43.0"

  // AWS SDK Dependencies
  lazy val awsDependencies: Seq[ModuleID] =
    Seq("software.amazon.awssdk" % "s3" % awsSdkVersion)

  lazy val logDependencies: Seq[ModuleID] = Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLogging,
    "ch.qos.logback" % "logback-classic" % "1.5.18",
  )

  // Testing Dependencies
  lazy val testingDependencies: Seq[ModuleID] = Seq(
    "org.scalatest" %% "scalatest" % "3.2.19" % Test,
    "com.dimafeng" %% "testcontainers-scala-scalatest" % testContainersVersion %
      Test,
    "com.dimafeng" %% "testcontainers-scala-localstack-v2" %
      testContainersVersion % Test,
  )

}
