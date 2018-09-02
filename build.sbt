lazy val melliteVersion        = "2.27.0"
lazy val PROJECT_VERSION       = melliteVersion
lazy val baseName              = "Mellite"

lazy val deps = new {
  val audioFile      = "1.5.0"
  val fscape         = "2.17.0"
  val lucre          = "3.9.1"
  val lucreSwing     = "1.11.0"
  val nuages         = "2.26.0"
  val osc            = "1.1.6"
  val patterns       = "0.4.0"
  val scalaCollider  = "1.27.0"
  val serial         = "1.1.1"
  val soundProcesses = "3.21.0"
  val span           = "1.4.1"
  val ugens          = "1.19.0"
}

scalaVersion in ThisBuild := "2.12.6"

val commonSettings = Seq(
  organization := "de.sciss",
  version      := PROJECT_VERSION
)

val lAudioFile          = RootProject(uri(s"https://github.com/Sciss/AudioFile.git#v${deps.audioFile}"))
val lFScape             = RootProject(uri(s"https://github.com/Sciss/FScape-next.git#v${deps.fscape}"))
val lLucreSwing         = RootProject(uri(s"https://github.com/Sciss/LucreSwing.git#v${deps.lucreSwing}"))
val lPatterns           = RootProject(uri(s"https://github.com/Sciss/Patterns.git#v${deps.patterns}"))
val lScalaCollider      = RootProject(uri(s"https://github.com/Sciss/ScalaCollider.git#v${deps.scalaCollider}"))
val lScalaColliderUGens = RootProject(uri(s"https://github.com/Sciss/ScalaColliderUGens.git#v${deps.ugens}"))
val lScalaOSC           = RootProject(uri(s"https://github.com/Sciss/ScalaOSC.git#v${deps.osc}"))
val lSerial             = RootProject(uri(s"https://github.com/Sciss/Serial.git#v${deps.serial}"))
val lSoundProcesses     = RootProject(uri(s"https://github.com/Sciss/SoundProcesses.git#v${deps.soundProcesses}"))
val lSpan               = RootProject(uri(s"https://github.com/Sciss/Span.git#v${deps.span}"))
val lMellite            = RootProject(uri(s"https://github.com/Sciss/${baseName}.git#v${PROJECT_VERSION}"))

val nuagesURI           = uri(s"https://github.com/Sciss/Wolkenpumpe.git#v${deps.nuages}")
val lNuagesCore         = ProjectRef(nuagesURI, "wolkenpumpe-core")
val lNuagesBasic        = ProjectRef(nuagesURI, "wolkenpumpe-basic")

val lucreURI            = uri(s"https://github.com/Sciss/Lucre.git#v${deps.lucre}")
val lLucreBase          = ProjectRef(lucreURI, "lucre-base")
val lLucreCore          = ProjectRef(lucreURI, "lucre-core")
val lLucreExpr          = ProjectRef(lucreURI, "lucre-expr")
val lLucreBdb6          = ProjectRef(lucreURI, "lucre-bdb6")

git.gitCurrentBranch in ThisBuild := "master"

lazy val unidocSettings = Seq(
  // site.addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), "latest/api"),
  mappings in packageDoc in Compile := (mappings  in (ScalaUnidoc, packageDoc)).value,
  unidocProjectFilter in (ScalaUnidoc, unidoc) := inAnyProject -- inProjects(lLucreBdb6)
)

val root = project.in(file("."))
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
      "org.scala-lang" % "scala-reflect" % scalaVersion.value // % "provided" // this is needed for sbt-unidoc to work with macros used by Mellite!
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
        "de.sciss.lucre.swing.edit",
        "de.sciss.lucre.swing.graph.impl",
        "de.sciss.lucre.swing.impl",
        "de.sciss.lucre.synth.expr.impl",
        "de.sciss.lucre.synth.impl",
        "de.sciss.mellite.gui.impl",
        "de.sciss.mellite.impl",
        "de.sciss.osc.impl", 
        "de.sciss.patterns.example", 
        "de.sciss.patterns.impl", 
        "de.sciss.patterns.stream.impl", 
        "de.sciss.serial.impl",
        "de.sciss.synth.impl",
        "de.sciss.synth.proc.graph.impl",
        "de.sciss.synth.proc.gui.impl",
        "de.sciss.synth.proc.impl",
        "de.sciss.synth.ugen.impl",
        "de.sciss.nuages.impl",
        "snippets"
      ).mkString(":"),
      "-doc-title", s"${baseName} ${PROJECT_VERSION} API"
    ),
    unidocProjectFilter in (ScalaUnidoc, unidoc) := inAnyProject -- inProjects(lNuagesBasic) -- inProjects(lLucreBdb6)
  )
  // XXX TODO --- don't know how to exclude bdb5/6 from lucre
  .aggregate(
    lAudioFile, 
    lFScape, 
    lLucreBase, 
    lLucreCore, 
    lLucreExpr, 
    lLucreSwing, 
    lNuagesCore, //  XXX TODO --- this is currently broken, sbt still tries to compile wolkenpumpe-basic which has a macro problem
    lPatterns,
    lScalaColliderUGens, 
    lScalaCollider, 
    lScalaOSC, 
    lSerial, 
    lSoundProcesses,
    lSpan,
    lMellite
  )

/*
val snippets = (project in file("snippets"))
  // .dependsOn(lMellite)
  .settings(
    name := s"$baseName-Snippets"
  )
*/
