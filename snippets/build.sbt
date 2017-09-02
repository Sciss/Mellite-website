lazy val baseName = "Mellite-Snippets"

name               := baseName
version            := "0.1.0-SNAPSHOT"
organization       := "de.sciss"
scalaVersion       := "2.12.3"
description        := "Tutorial Snippets for SoundProcesses and Mellite"
licenses           := Seq("GPL v3+" -> url("http://www.gnu.org/licenses/gpl-3.0.txt"))

resolvers          += "Oracle Repository" at "http://download.oracle.com/maven"  // required for sleepycat

// ---- main dependencies ----

lazy val melliteVersion = "2.16.0"

libraryDependencies ++= Seq(
  "de.sciss" %% "mellite" % melliteVersion
)

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-Xfuture", "-encoding", "utf8", "-Xlint")

