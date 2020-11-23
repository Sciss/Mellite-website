lazy val melliteVersion        = "3.2.1"
lazy val PROJECT_VERSION       = melliteVersion
lazy val baseName              = "Mellite"
lazy val baseNameL             = baseName.toLowerCase
lazy val repoName              = s"$baseName-website"

lazy val deps = new {
  val audioFile      = "2.3.1"
  val desktop        = "0.11.3"
  val fscape         = "3.3.0"
  val lucre          = "4.2.0"
  val lucrePi        = "1.2.0"
  val lucreSwing     = "2.4.1"
  val nuages         = "3.2.0"
  val osc            = "1.2.3"
  val patterns       = "1.2.0"
  val scalaCollider  = "2.4.0"
  val serial         = "2.0.0"
  val soundProcesses = "4.4.0"
  val span           = "2.0.0"
  val ugens          = "1.20.0"
}

scalaVersion in ThisBuild := "2.13.4"

mimaFailOnNoPrevious in ThisBuild := false

val commonSettings = Seq(
  organization := "de.sciss",
  version      := PROJECT_VERSION
)

val audioFileURI        = uri(s"https://github.com/Sciss/AudioFile.git#v${deps.audioFile}")
val lAudioFile          = ProjectRef(audioFileURI, "rootJVM")
val xAudioFile          = ProjectRef(audioFileURI, "rootJS")

val lDesktop            = RootProject(uri(s"https://github.com/Sciss/Desktop.git#v${deps.desktop}"))
val lLucrePi            = RootProject(uri(s"https://github.com/Sciss/LucrePi.git#v${deps.lucrePi}"))

val lucreSwingURI       = uri(s"https://github.com/Sciss/LucreSwing.git#v${deps.lucreSwing}")
val lLucreSwing         = ProjectRef(lucreSwingURI, "rootJVM")
val xLucreSwing         = ProjectRef(lucreSwingURI, "rootJS")

val patternsURI         = uri(s"https://github.com/Sciss/Patterns.git#v${deps.patterns}")
val lPatternsCore       = ProjectRef(patternsURI, "coreJVM")
val xPatternsCore       = ProjectRef(patternsURI, "coreJS")
val lPatternsLucre      = ProjectRef(patternsURI, "lucreJVM")
val xPatternsLucre      = ProjectRef(patternsURI, "lucreJS")

val scalaColliderURI    = uri(s"https://github.com/Sciss/ScalaCollider.git#v${deps.scalaCollider}")
val lScalaCollider      = ProjectRef(scalaColliderURI, "rootJVM")
val xScalaCollider      = ProjectRef(scalaColliderURI, "rootJS")

val scalaColliderUGensURI = uri(s"https://github.com/Sciss/ScalaColliderUGens.git#1942b8ec6508ac3d9097ee16362dbd3fc768c188")
val lScalaColliderUGensAPI = ProjectRef(scalaColliderUGensURI, "apiJVM")
val xScalaColliderUGensAPI = ProjectRef(scalaColliderUGensURI, "apiJS")
val lScalaColliderUGensCore = ProjectRef(scalaColliderUGensURI, "coreJVM")
val xScalaColliderUGensCore = ProjectRef(scalaColliderUGensURI, "coreJS")
val lScalaColliderUGensPlugins = ProjectRef(scalaColliderUGensURI, "pluginsJVM")
val xScalaColliderUGensPlugins = ProjectRef(scalaColliderUGensURI, "pluginsJS")

val scalaOSCURI         = uri(s"https://github.com/Sciss/ScalaOSC.git#v${deps.osc}")
val lScalaOSC           = ProjectRef(scalaOSCURI, "rootJVM")
val xScalaOSC           = ProjectRef(scalaOSCURI, "rootJS")

// val serialURI           = uri(s"https://github.com/Sciss/Serial.git#v${deps.serial}")
val serialURI           = uri(s"https://github.com/Sciss/Serial.git#518d3512f851a688914edb8a7bdbe0cd53d00e5d")
val lSerial             = ProjectRef(serialURI, "rootJVM")
val xSerial             = ProjectRef(serialURI, "rootJS")

val soundProcessesURI   = uri(s"https://github.com/Sciss/SoundProcesses.git#v${deps.soundProcesses}")
val lSoundProcessesCore = ProjectRef(soundProcessesURI, "coreJVM")
val xSoundProcessesCore = ProjectRef(soundProcessesURI, "coreJS")
val lSoundProcessesSynth = ProjectRef(soundProcessesURI, "synthJVM")
val xSoundProcessesSynth = ProjectRef(soundProcessesURI, "synthJS")
val lSoundProcessesViews = ProjectRef(soundProcessesURI, "viewsJVM")
val xSoundProcessesViews = ProjectRef(soundProcessesURI, "viewsJS")

// val spanURI             = uri(s"https://github.com/Sciss/Span.git#v${deps.span}")
val spanURI             = uri(s"https://github.com/Sciss/Span.git#7cd849885e855debfea963c1f52524207af406c5")
val lSpan               = ProjectRef(spanURI, "rootJVM")
val xSpan               = ProjectRef(spanURI, "rootJS")

val nuagesURI           = uri(s"https://github.com/Sciss/Wolkenpumpe.git#v${deps.nuages}")
val lNuagesCore         = ProjectRef(nuagesURI, "wolkenpumpe-core")
val xNuagesBasic        = ProjectRef(nuagesURI, "wolkenpumpe-basic")

val fscapeURI           = uri(s"https://github.com/Sciss/FScape-next.git#v${deps.fscape}")
val lFScapeCore         = ProjectRef(fscapeURI, "coreJVM")
val xFScapeCore         = ProjectRef(fscapeURI, "coreJS")
val lFScapeLucre        = ProjectRef(fscapeURI, "lucreJVM")
val xFScapeLucre        = ProjectRef(fscapeURI, "lucreJS")
val lFScapeViews        = ProjectRef(fscapeURI, "fscape-views")

val lucreURI            = uri(s"https://github.com/Sciss/Lucre.git#v${deps.lucre}")
// val lucreURI            = uri(s"https://github.com/Sciss/Lucre.git#993332e1bafb016e867b4736966c2170e5fefc23")  // unidoc problem fix
val lLucreAdjunct       = ProjectRef(lucreURI, "adjunctJVM")
val xLucreAdjunct       = ProjectRef(lucreURI, "adjunctJS")
val lLucreBase          = ProjectRef(lucreURI, "baseJVM")
val xLucreBase          = ProjectRef(lucreURI, "baseJS")
val xLucreConfluent     = ProjectRef(lucreURI, "confluentJS")
val lLucreCore          = ProjectRef(lucreURI, "coreJVM")
val xLucreCore          = ProjectRef(lucreURI, "coreJS")
val lLucreData          = ProjectRef(lucreURI, "dataJVM")
val xLucreData          = ProjectRef(lucreURI, "dataJS")
val lLucreExpr          = ProjectRef(lucreURI, "exprJVM")
val xLucreExpr          = ProjectRef(lucreURI, "exprJS")
val xLucreGeom          = ProjectRef(lucreURI, "geomJS")
val lLucreBdb           = ProjectRef(lucreURI, "lucre-bdb")

val melliteURI          = uri(s"https://github.com/Sciss/${baseName}.git#v${PROJECT_VERSION}")
//val melliteURI          = uri(s"https://github.com/Sciss/${baseName}.git#86042869528e6a847ac09804cebe3cedcb8f1099")
val lMelliteCore        = ProjectRef(melliteURI, s"$baseNameL-core")
val lMelliteApp         = ProjectRef(melliteURI, s"$baseNameL-app")

lazy val lList = Seq(
  lAudioFile,
  lDesktop,
  lFScapeCore, lFScapeLucre, lFScapeViews,
  lLucreAdjunct, lLucreBase, lLucreCore, lLucreData, lLucreExpr,
  lLucrePi,
  lLucreSwing,
  lNuagesCore, //  XXX TODO --- this is currently broken, sbt still tries to compile wolkenpumpe-basic which has a macro problem
  lPatternsCore, lPatternsLucre,
  lScalaColliderUGensAPI, lScalaColliderUGensCore, lScalaColliderUGensPlugins,
  lScalaCollider,
  lScalaOSC,
  lSerial,
  lSoundProcessesCore, lSoundProcessesSynth, lSoundProcessesViews,
  lSpan,
  lMelliteCore, lMelliteApp,
)

// git.gitCurrentBranch in ThisBuild := "main"

lazy val unidocSettings = Seq(
  // site.addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), "latest/api"),
  mappings in packageDoc in Compile := (mappings in (ScalaUnidoc, packageDoc)).value,
  unidocProjectFilter in (ScalaUnidoc, unidoc) := inAnyProject -- inProjects(
    xAudioFile, 
    xFScapeCore, xFScapeLucre, 
    xLucreAdjunct, xLucreBase, xLucreConfluent, xLucreCore, xLucreData, xLucreExpr, xLucreGeom,
    xLucreSwing,
    xPatternsCore, xPatternsLucre,
    xScalaColliderUGensAPI, xScalaColliderUGensCore, xScalaColliderUGensPlugins,
    xScalaCollider,
    xScalaOSC,
    xSerial,
    xSoundProcessesCore, xSoundProcessesSynth, xSoundProcessesViews,
    xSpan,
    xNuagesBasic
  ),
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value // % "provided" // this is needed for sbt-unidoc to work with macros used by Mellite!
  ),
  scalacOptions in (Compile, doc) ++= Seq(
    "-skip-packages", Seq(
      "akka.stream.sciss",
      "scala.tools",
      "de.sciss.desktop",
      "de.sciss.fscape.graph.impl",
      "de.sciss.fscape.lucre.impl",
      "de.sciss.fscape.lucre.stream",
      "de.sciss.fscape.stream",
      "de.sciss.fscape.modules",
      "de.sciss.lucre.artifact.impl",
      "de.sciss.lucre.adjunct.impl",
      "de.sciss.lucre.bitemp.impl",
      "de.sciss.lucre.confluent.impl",
      "de.sciss.lucre.event.impl",
      "de.sciss.lucre.expr.impl",
      "de.sciss.lucre.expr.graph.impl",
      "de.sciss.lucre.impl",
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
      "de.sciss.patterns.lucre.impl", 
      "de.sciss.patterns.stream", 
      "de.sciss.patterns.stream.impl", 
      "de.sciss.serial.impl",
      "de.sciss.synth.impl",
      "de.sciss.synth.proc.graph.impl",
      "de.sciss.proc.gui.impl",
      "de.sciss.proc.impl",
      "de.sciss.synth.ugen.impl",
      "de.sciss.nuages.impl",
      "snippets"
    ).mkString(":"),
    "-doc-title", s"${baseName} ${PROJECT_VERSION} API",
    // "-Ymacro-no-expand"
  )
)

///////////////////// site

val site = project.withId(s"$baseNameL-site").in(file("."))
  .settings(commonSettings: _*)
  .enablePlugins(ScalaUnidocPlugin).settings(unidocSettings)
  // .enablePlugins(GhpagesPlugin)
  .enablePlugins(ParadoxSitePlugin, SiteScaladocPlugin)
  .settings(
    name                           := baseName,  // used by GhpagesPlugin, must be base base!
    siteSubdirName in SiteScaladoc := "latest/api",
    // git.remoteRepo                 := s"git@github.com:Sciss/${baseName}.git",
    // git.gitCurrentBranch           := "main",
    paradoxTheme                   := Some(builtinParadoxTheme("generic")),
    paradoxProperties /* in Paradox */ ++= Map(
      "image.base_url"       -> "assets/images",
      "github.base_url"      -> "https://github.com/Sciss/Mellite-website",
      "snip.sp_tut.base_dir" -> s"${baseDirectory.value}/snippets/src/main/scala/de/sciss/tutorial"
    ),
  )
  .aggregate(lList: _*)

/*
val snippets = (project in file("snippets"))
  // .dependsOn(lMellite)
  .settings(
    name := s"$baseName-Snippets"
  )
*/

////////////////////////// unidoc publishing

// In order to publish only the scala-docs coming out
// of sbt-unidoc, we must create an auxiliary module
// 'pub' depending on the aggregating module 'aggr'.
// There, we copy the setting of `packageDoc`. This way,
// we can publish using `sbt scalacollider-unidoc/publishLocal` etc.
// cf. https://github.com/sbt/sbt-unidoc/issues/65

lazy val aggr = project.withId(s"$baseNameL-aggr").in(file("aggr"))
  .enablePlugins(ScalaUnidocPlugin)
  .settings(unidocSettings)
  .aggregate(lList: _*)

lazy val pub = project.withId(s"$baseNameL-unidoc").in(file("pub"))
  .settings(commonSettings)
  .settings(publishSettings)
  .settings(
    name                  := s"$baseName-unidoc",
    version               := PROJECT_VERSION,
    organization          := "de.sciss",
    autoScalaLibrary      := false,
    licenses              := Seq("CC BY-SA 4.0" -> url("https://creativecommons.org/licenses/by-sa/4.0/")), // required for Maven Central
    homepage              := Some(url(s"https://git.iem.at/sciss/$baseName")),
    packageDoc in Compile := (packageDoc in Compile in aggr).value,
    publishArtifact in (Compile, packageBin) := false, // there are no binaries
    publishArtifact in (Compile, packageSrc) := false, // there are no sources
  )
 
lazy val publishSettings = Seq(
  publishMavenStyle := true,
  publishTo :=
    Some(if (isSnapshot.value)
      "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
    else
      "Sonatype Releases"  at "https://oss.sonatype.org/service/local/staging/deploy/maven2"
    ),
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  pomExtra := {
    <scm>
      <url>git@git.iem.at:sciss/{repoName}.git</url>
      <connection>scm:git:git@git.iem.at:sciss/{repoName}.git</connection>
    </scm>
    <developers>
      <developer>
        <id>sciss</id>
        <name>Hanns Holger Rutz</name>
        <url>http://www.sciss.de</url>
      </developer>
    </developers>
  }
)

