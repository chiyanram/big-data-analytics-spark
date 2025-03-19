import com.softwaremill.SbtSoftwareMillCommon.commonSmlBuildSettings
import com.softwaremill.SbtSoftwareMillExtra.dependencyCheckSettings
import com.softwaremill.SbtSoftwareMillExtra.dependencyUpdatesSettings

import Dependencies.*

lazy val startupTransition: State => State = "conventionalCommits" :: _
Global / onLoad := startupTransition.compose((Global / onLoad).value)

inThisBuild {
  List(
    description := "Workspace to try and learn spark",
    organization := "com.rmurugaian.scala",
    scalaVersion := "2.13.16",
    Test / parallelExecution := false,
    Test / fork := false,
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-Yrangepos"),
    javacOptions ++= Seq("-source", "17", "-target", "17"),
  )
}

addCommandAlias(
  "lint",
  "; scalafmtSbtCheck; scalafmtCheckAll; Compile/scalafix --check; Test/scalafix --check",
)
addCommandAlias(
  "fix",
  "; Compile/scalafix; Test/scalafix; scalafmtSbt; scalafmtAll",
)
addCommandAlias("ciTest", "; coverage; test; coverageReport; coverageOff")

// Extra settings
lazy val extraSmlBuildSettings = commonSmlBuildSettings ++
  dependencyUpdatesSettings ++ dependencyCheckSettings

//noinspection scala2InSource3
lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin, DockerPlugin).settings(extraSmlBuildSettings)
  .settings(
    Test / scalacOptions ++= Seq(
      "-Wconf:msg=unused value of type org.scalatest.Assertion:s",
      "-Wconf:msg=unused value of type org.scalamock:s",
    ),
  ).settings(
    name := "big-data-analytics-spark",
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "com.rmurugaian.scala.common",
  ).settings().settings(assembly / assemblyMergeStrategy := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case PathList(ps @ _*) if ps.last.endsWith(".class") => MergeStrategy.first
    case _ => MergeStrategy.first
  }).settings(
    libraryDependencies ++= awsDependencies ++ testingDependencies ++
      logDependencies,
  ).settings(
    docker / dockerfile := {
      // The assembly task generates a fat JAR file
      val artifact: File = assembly.value
      val artifactTargetPath = s"/app/${artifact.name}"

      new Dockerfile {
        from("eclipse-temurin:17-jre-alpine")
        add(artifact, artifactTargetPath)
        entryPoint("java", "-jar", artifactTargetPath)
      }
    },
    docker / buildOptions := BuildOptions(
      cache = false,
      removeIntermediateContainers = BuildOptions.Remove.Always,
      pullBaseImage = BuildOptions.Pull.Always,
      platforms = List("linux/arm64"),
    ),
    docker / imageNames := Seq(ImageName(
      namespace = Some("ramachandran09"),
      repository = "library",
      tag = Some(version.value),
    )),
  )
