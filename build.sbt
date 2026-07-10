/// publishing

name := "emoji"
organization := "com.lightbend"
homepage := Some(url("https://github.com/lightbend/lightbend-emoji"))
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
scmInfo := Some(ScmInfo(
  url("https://github.com/lightbend/lightbend-emoji"),
  "scm:git:git@github.com:lightbend/lightbend-emoji.git"
))
developers := List(
  Developer(
    id = "Lightbend",
    name = "Lightbend, Inc.",
    email = "",
    url = url("https://www.lightbend.com")
  )
)

ThisBuild / dynverVTagPrefix := false
ThisBuild / versionScheme := Some("early-semver")

/// build

crossScalaVersions := Seq("2.13.18", "3.3.8")
scalaVersion := crossScalaVersions.value.head

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest-wordspec" % "3.2.20" % Test,
  "org.scalatest" %% "scalatest-shouldmatchers" % "3.2.20" % Test,
  "org.scalameta" %% "munit" % "1.3.4" % Test,
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings") ++ (
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, _)) => Seq("-Xlint")
    case _            => Seq.empty
  }
)

Compile / console / scalacOptions ~=
  (_ filterNot Set(
    "-Xlint",
    "-Xfatal-warnings"
  ))

Test / scalacOptions ~=
  (_ filterNot Set(
    "-Xfatal-warnings"
  ))

console / initialCommands := {
  """import com.lightbend.emoji._
    |import com.lightbend.emoji.Emoji.Implicits._
    |import com.lightbend.emoji.ShortCodes.Implicits._
    |""".stripMargin
}

/// MiMa

mimaPreviousArtifacts := Set(organization.value %% name.value % "1.3.0")
