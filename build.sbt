lazy val melliteVersion        = "2.16.0"
lazy val PROJECT_VERSION       = melliteVersion
lazy val baseName              = "Mellite"

lazy val soundProcessesVersion = "3.13.0"
lazy val nuagesVersion         = "2.17.0"
lazy val oscVersion            = "1.1.5"
lazy val audioFileVersion      = "1.4.6"
lazy val scalaColliderVersion  = "1.22.4"
lazy val ugensVersion          = "1.16.4"
lazy val fscapeVersion         = "2.8.0"
lazy val lucreVersion          = "3.4.1"
lazy val spanVersion           = "1.3.2"

scalaVersion in ThisBuild := "2.12.3"

val commonSettings = Seq(
  organization := "de.sciss",
  version      := PROJECT_VERSION
)

val lSpan               = RootProject(uri(s"git://github.com/Sciss/Span.git#v${spanVersion}"))
val lScalaOSC           = RootProject(uri(s"git://github.com/Sciss/ScalaOSC.git#v${oscVersion}"))
val lScalaAudioFile     = RootProject(uri(s"git://github.com/Sciss/ScalaAudioFile.git#v${audioFileVersion}"))
val lScalaColliderUGens = RootProject(uri(s"git://github.com/Sciss/ScalaColliderUGens.git#v${ugensVersion}"))
val lScalaCollider      = RootProject(uri(s"git://github.com/Sciss/ScalaCollider.git#v${scalaColliderVersion}"))
val lFScape             = RootProject(uri(s"git://github.com/Sciss/FScape-next.git#v${fscapeVersion}"))
val lSoundProcesses     = RootProject(uri(s"git://github.com/Sciss/SoundProcesses.git#v${soundProcessesVersion}"))
val lNuages             = RootProject(uri(s"git://github.com/Sciss/Wolkenpumpe.git#v${nuagesVersion}"))
val lMellite            = RootProject(uri(s"git://github.com/Sciss/${baseName}.git#v${PROJECT_VERSION}"))

val lucreURI            = uri(s"git://github.com/Sciss/Lucre.git#v${lucreVersion}")
val lLucreCore          = ProjectRef(lucreURI, "lucre-core")
val lLucreExpr          = ProjectRef(lucreURI, "lucre-expr")
val lLucreBdb6          = ProjectRef(lucreURI, "lucre-bdb6")

git.gitCurrentBranch in ThisBuild := "master"

lazy val unidocSettings = Seq(
  // site.addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), "latest/api"),
  mappings in packageDoc in Compile := (mappings  in (ScalaUnidoc, packageDoc)).value,
  unidocProjectFilter in (ScalaUnidoc, unidoc) := inAnyProject -- inProjects(lLucreBdb6)
)

val root = (project in file("."))
  .settings(commonSettings: _*)
  .enablePlugins(ScalaUnidocPlugin).settings(unidocSettings)
  .enablePlugins(GhpagesPlugin, ParadoxSitePlugin, SiteScaladocPlugin)
  .settings(
    name                           := baseName,
    siteSubdirName in SiteScaladoc := "latest/api",
    git.remoteRepo                 := s"git@github.com:Sciss/${baseName}.git",
    git.gitCurrentBranch           := "master",
    paradoxTheme                   := Some(builtinParadoxTheme("generic")),
    paradoxProperties in Paradox ++= Map(
      "image.base_url"       -> "assets/images",
      "github.base_url"      -> "https://github.com/Sciss/Mellite-website",
      "snip.sp_tut.base_dir" -> s"${baseDirectory.value}/snippets/src/main/scala/de/sciss/soundprocesses/tutorial"
    ),
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided" // this is needed for sbt-unidoc to work with macros used by Mellite!
    ),
    scalacOptions in (Compile, doc) ++= Seq(
      "-skip-packages", Seq(
        "akka.stream.sciss",
        "scala.tools",
        "de.sciss.fscape.graph.impl",
        "de.sciss.fscape.lucre.impl",
        "de.sciss.fscape.lucre.stream",
        "de.sciss.fscape.stream",
        "de.sciss.lucre.artifact.impl",
        "de.sciss.lucre.bitemp.impl",
        "de.sciss.lucre.confluent.impl",
        "de.sciss.lucre.event.impl",
        "de.sciss.lucre.expr.impl",
        "de.sciss.lucre.stm.impl",
        "de.sciss.lucre.synth.expr.impl",
        "de.sciss.lucre.synth.impl",
        "de.sciss.mellite.gui.impl",
        "de.sciss.mellite.impl",
        "de.sciss.osc.impl", 
        "de.sciss.synth.impl",
        "de.sciss.synth.proc.graph.impl",
        "de.sciss.synth.proc.gui.impl",
        "de.sciss.synth.proc.impl",
        "de.sciss.synth.ugen.impl",
        "de.sciss.nuages.impl",
        "snippets"
      ).mkString(":"),
      "-doc-title", s"${baseName} ${PROJECT_VERSION} API"
    )
  )
  // XXX TODO --- don't know how to exclude bdb5/6 from lucre
  .aggregate(
    /* currently uses Scala 2.11 and incompatible site plugin: lSpan, */
    lScalaOSC, 
    lScalaAudioFile, 
    lScalaColliderUGens, 
    lScalaCollider, 
    lFScape, 
    lSoundProcesses, 
    lNuages, 
    lLucreCore, 
    lLucreExpr, 
    lMellite
  )

val snippets = (project in file("snippets"))
  // .dependsOn(lMellite)
  .settings(
    name := s"$baseName-Snippets"
  )

