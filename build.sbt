import com.softwaremill.SbtSoftwareMillCommon.commonSmlBuildSettings

lazy val commonSettings = commonSmlBuildSettings ++ Seq(
  organization := "com.rmurugaian.spark",
  scalaVersion := "2.13.15",
  versionScheme := Some("early-semver"),
)

ThisBuild / wartremoverErrors ++= Warts.allBut(Wart.Overloading, Wart.Equals)

val sparkSql = "org.apache.spark" %% "spark-sql" % "3.5.4"
val sparkCore = "org.apache.spark" %% "spark-core" % "3.5.4"
val scalaTest = "org.scalatest" %% "scalatest" % "3.2.19" % Test

lazy val rootProject = (project in file("."))
  .settings(commonSettings *)
  .settings(
    publishArtifact := false,
    name := "big-data-analytics-spark",
    jacocoAggregateReportSettings := JacocoReportSettings(
      title = "mdm coverage report",
      formats = Seq(JacocoReportFormats.XML),
    ),
    publish / skip := true,
  )
  .aggregate(module1)

lazy val module1: Project = (project in file("module1"))
  .settings(commonSettings *)
  .settings(
    name := "module1",
    libraryDependencies ++= Seq(
      scalaTest,
      sparkSql,
    ),
  )
