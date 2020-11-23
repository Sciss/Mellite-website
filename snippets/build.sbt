lazy val baseName = "Mellite-Snippets"

lazy val deps = new {
  val mellite             = "3.2.0"
  val fscape              = "3.3.0"
  val soundProcesses      = "4.4.0"
  val scalaColliderUGens  = "1.20.0"
}

lazy val commonSettings = Seq(
  name               := baseName,
  version            := "0.1.0-SNAPSHOT",
  organization       := "de.sciss",
  scalaVersion       := "2.13.4",
  description        := "Tutorial Snippets for SoundProcesses and Mellite",
  licenses           := Seq("AGPL v3+" -> url("http://www.gnu.org/licenses/agpl-3.0.txt")),
  scalacOptions ++= Seq(
    "-deprecation", "-unchecked", "-feature", "-encoding", "utf8", "-Xlint", "-Yrangepos", "-Xsource:2.13"
  )
)

lazy val root = project.in(file("."))
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "de.sciss" %% "fscape-core"                 % deps.fscape,
      "de.sciss" %% "mellite-core"                % deps.mellite,
      "de.sciss" %% "scalacolliderugens-plugins"  % deps.scalaColliderUGens,
      "de.sciss" %% "soundprocesses-compiler"     % deps.soundProcesses,
    )
  )

