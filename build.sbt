name := "equalator"

scalaVersion := "2.12.2"




import sbt._

import sbtcrossproject.{crossProject, CrossType}

releaseCrossBuild := true
publishMavenStyle := true
pomIncludeRepository := (_ => false)
releasePublishArtifactsAction := PgpKeys.publishSigned.value

lazy val defaultSettings =
  Project.defaultSettings ++
    Compiler.defaultSettings ++
    Publish.defaultSettings ++
    Tests.defaultSettings ++
    Console.defaultSettings

lazy val equalator = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .settings(defaultSettings: _*)
  .jsSettings(
    crossScalaVersions := Versions.ScalaCross,
    parallelExecution in Test := false
  )

lazy val root = project.in(file("."))
  .settings(defaultSettings: _*)
  .settings(
    name := "equalator",
    publish := {},
    publishLocal := {},
    publishArtifact := false
  )
  .aggregate(equalatorJVM, equalatorJS)

lazy val equalatorJVM = equalator.jvm
lazy val equalatorJS = equalator.js