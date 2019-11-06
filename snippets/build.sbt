lazy val baseName = "Mellite-Snippets"

name               := baseName
version            := "0.1.0-SNAPSHOT"
organization       := "de.sciss"
scalaVersion       := "2.12.10"
description        := "Tutorial Snippets for SoundProcesses and Mellite"
licenses           := Seq("AGPL v3+" -> url("http://www.gnu.org/licenses/agpl-3.0.txt"))

resolvers          += "Oracle Repository" at "http://download.oracle.com/maven"  // required for sleepycat

// ---- main dependencies ----

lazy val melliteVersion = "2.41.0"

libraryDependencies ++= Seq(
  "de.sciss" %% "mellite" % melliteVersion
)

scalacOptions ++= Seq(
  "-deprecation", "-unchecked", "-feature", "-encoding", "utf8", "-Xlint", "-Yrangepos", "-Xsource:2.13"
)
