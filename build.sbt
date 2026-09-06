/// publishing

name := "emoji"
organization := "com.lightbend"
homepage := Some(uri("https://github.com/lightbend/lightbend-emoji"))
licenses += ("Apache-2.0", uri("https://www.apache.org/licenses/LICENSE-2.0.html"))
scmInfo := Some(ScmInfo(
  uri("https://github.com/lightbend/lightbend-emoji"),
  "scm:git:git@github.com:lightbend/lightbend-emoji.git"
))
developers := List(
  Developer(
    id = "Lightbend",
    name = "Lightbend, Inc.",
    email = "",
    url = uri("https://www.lightbend.com")
  )
)

ThisBuild / dynverVTagPrefix := false
ThisBuild / versionScheme := Some("early-semver")

/// build

crossScalaVersions := Seq("2.13.18", "3.9.0")
scalaVersion := crossScalaVersions.value.head

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest-wordspec" % "3.2.20" % Test,
  "org.scalatest" %% "scalatest-shouldmatchers" % "3.2.20" % Test,
  "org.scalameta" %% "munit" % "1.3.6" % Test,
)

scalacOptions ++= Seq("-release:17", "-unchecked", "-deprecation", "-feature", "-Werror") ++ (
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, _)) => Seq("-Xlint")
    case _            => Seq.empty
  }
)

Compile / console / scalacOptions ~=
  (_ filterNot Set(
    "-Xlint",
    "-Werror"
  ))

Test / scalacOptions ~=
  (_ filterNot Set(
    "-Werror"
  ))

console / initialCommands := {
  """import com.lightbend.emoji._
    |import com.lightbend.emoji.Emoji.Implicits._
    |import com.lightbend.emoji.ShortCodes.Implicits._
    |""".stripMargin
}

/// MiMa

import com.typesafe.tools.mima.core.*, ProblemFilters.*
mimaPreviousArtifacts := Set(organization.value %% name.value % "1.3.0")
// this can be removed once the reference version is bumped
mimaBinaryIssueFilters ++= Seq(
  exclude[MissingClassProblem]("com.lightbend.emoji.ScalaVersionSpecific"),
  exclude[MissingClassProblem]("com.lightbend.emoji.ScalaVersionSpecific$"),
  // Drop once the fix for scala-garden/mima#794 is released.
  exclude[DirectMissingMethodProblem]("scala.util.parsing.input.OffsetPosition.<clinit>"),
  exclude[DirectMissingMethodProblem]("com.lightbend.emoji.ShortCodes.<clinit>"),
)
