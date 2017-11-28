import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._


object Versions {
  val Equalator = "1.1"
  val Scala = "2.12.2"
  val ScalaCross = Seq("2.12.2", "2.11.11")

}

object Project {
  val defaultSettings = Seq(

    organization in ThisBuild := "org.rudogma",
    name := "equalator",
    version in ThisBuild := Versions.Equalator,

    licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT")),

    homepage := Some(url("https://github.com/Rudogma/scala-equalator")),

    libraryDependencies ++= Seq(
      "com.chuusai" %% "shapeless" % "2.3.2"
    )
  )

}

object Compiler {

  val defaultSettings = Seq(
    scalacOptions in ThisBuild ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-unchecked",
      "-Xfatal-warnings",
      "-Yno-adapted-args",
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Ywarn-unused-import"
    ),

    scalaVersion in ThisBuild := Versions.Scala,
    crossScalaVersions := Versions.ScalaCross
  )
}
object Publish {
  val defaultSettings = Seq(

    description := "Deep equals. Type safe, compile time comparison chain. Based on shapeless.LabelledGeneric",
    developers += Developer(
      "rudogma",
      "Mikhail Savinov",
      "mikhail@rudogma.org",
      url("https://github.com/rudogma")
    ),

    publishTo in ThisBuild := Some(if (isSnapshot.value){
      Opts.resolver.sonatypeSnapshots
    }else{
      Opts.resolver.sonatypeStaging
    }),

    publishMavenStyle := true,

    publishArtifact in Test := false,

    pomIncludeRepository := { _ => false },

    //    releasePublishArtifactsAction := PgpKeys.publishSigned.value,

    licenses += ("MIT", url("https://opensource.org/licenses/MIT")),

    scmInfo := Some(
      ScmInfo(
        url("https://github.com/rudogma/scala-equalator"),
        "scm:git:git@github.com:rudogma/scala-equalator.git",
        Some("scm:git:ssh://github.com:rudogma/scala-equalator.git")
      )
    )
  )
}

object Tests {
  val defaultSettings = Seq(
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "spire" % "0.14.1" % "test",
      "org.scalatest" %%% "scalatest" % "3.0.3" % "test",
      "org.scalacheck" %%% "scalacheck" % "1.13.5" % "test"
    )
  )
}


object Console {
  val defaultSettings = Seq(
    scalacOptions ~= (_ filterNot (Set("-Xfatal-warnings", "-Ywarn-unused-import").contains)),

    initialCommands in console := """
       |import org.rudogma.equalator._
       |import org.rudogma.equalator.Implicits._
    """.stripMargin
  )
}