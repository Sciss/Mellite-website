lazy val melliteVersion        = "2.43.0"
lazy val PROJECT_VERSION       = melliteVersion
lazy val baseName              = "Mellite"
lazy val baseNameL             = baseName.toLowerCase
lazy val repoName              = s"$baseName-website"

lazy val deps = new {
  val audioFile      = "1.5.3"
  val desktop        = "0.10.5"
  val fscape         = "2.33.1"
  val lucre          = "3.16.1"
  val lucreSwing     = "1.20.0"
  val nuages         = "2.38.0"
  val osc            = "1.2.1"
  val patterns       = "0.17.0"
  val scalaCollider  = "1.28.5"
  val serial         = "1.1.1"
  val soundProcesses = "3.33.0"
  val span           = "1.4.3"
//  val ugens          = "1.19.6"
}

scalaVersion in ThisBuild := "2.12.10"

val commonSettings = Seq(
  organization := "de.sciss",
  version      := PROJECT_VERSION
)

val lAudioFile          = RootProject(uri(s"https://github.com/Sciss/AudioFile.git#v${deps.audioFile}"))
val lDesktop            = RootProject(uri(s"https://github.com/Sciss/Desktop.git#v${deps.desktop}"))
val lLucreSwing         = RootProject(uri(s"https://github.com/Sciss/LucreSwing.git#v${deps.lucreSwing}"))
val lPatterns           = RootProject(uri(s"https://github.com/Sciss/Patterns.git#v${deps.patterns}"))
val lScalaCollider      = RootProject(uri(s"https://github.com/Sciss/ScalaCollider.git#v${deps.scalaCollider}"))
// val lScalaColliderUGens = RootProject(uri(s"https://github.com/Sciss/ScalaColliderUGens.git#v${deps.ugens}"))
val lScalaColliderUGens = RootProject(uri(s"https://github.com/Sciss/ScalaColliderUGens.git#61b0ec131c4ac9fbb934db4d62bed5ccad8dfc2d")) // unidoc problem fix
val lScalaOSC           = RootProject(uri(s"https://github.com/Sciss/ScalaOSC.git#v${deps.osc}"))
val lSerial             = RootProject(uri(s"https://github.com/Sciss/Serial.git#v${deps.serial}"))
val lSoundProcesses     = RootProject(uri(s"https://github.com/Sciss/SoundProcesses.git#v${deps.soundProcesses}"))
val lSpan               = RootProject(uri(s"https://github.com/Sciss/Span.git#v${deps.span}"))
// val lMellite            = RootProject(uri(s"https://github.com/Sciss/${baseName}.git#v${PROJECT_VERSION}"))

val nuagesURI           = uri(s"https://github.com/Sciss/Wolkenpumpe.git#v${deps.nuages}")
val lNuagesCore         = ProjectRef(nuagesURI, "wolkenpumpe-core")
val lNuagesBasic        = ProjectRef(nuagesURI, "wolkenpumpe-basic")

val fscapeURI           = uri(s"https://github.com/Sciss/FScape-next.git#v${deps.fscape}")
val lFScapeCore         = ProjectRef(fscapeURI, "fscape-core")
val lFScapeLucre        = ProjectRef(fscapeURI, "fscape-lucre")
val lFScapeViews        = ProjectRef(fscapeURI, "fscape-views")

val lucreURI            = uri(s"https://github.com/Sciss/Lucre.git#v${deps.lucre}")
// val lucreURI            = uri(s"https://github.com/Sciss/Lucre.git#993332e1bafb016e867b4736966c2170e5fefc23")  // unidoc problem fix
val lLucreAdjunct       = ProjectRef(lucreURI, "lucre-adjunct")
val lLucreBase          = ProjectRef(lucreURI, "lucre-base")
val lLucreCore          = ProjectRef(lucreURI, "lucre-core")
val lLucreExpr          = ProjectRef(lucreURI, "lucre-expr")
val lLucreBdb           = ProjectRef(lucreURI, "lucre-bdb")

val melliteURI          = uri(s"https://github.com/Sciss/${baseName}.git#v${PROJECT_VERSION}")
val lMelliteCore        = ProjectRef(melliteURI, s"$baseNameL-core")
val lMelliteApp         = ProjectRef(melliteURI, s"$baseNameL-app")

lazy val lList = Seq(
  lAudioFile,
  lDesktop,
  lFScapeCore,
  lFScapeLucre,
  lFScapeViews,
  lLucreAdjunct,
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
  lMelliteCore,
  lMelliteApp,
)

// git.gitCurrentBranch in ThisBuild := "master"

lazy val unidocSettings = Seq(
  // site.addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), "latest/api"),
  mappings in packageDoc in Compile := (mappings  in (ScalaUnidoc, packageDoc)).value,
  unidocProjectFilter in (ScalaUnidoc, unidoc) := inAnyProject -- inProjects(lNuagesBasic), // -- inProjects(lLucreBdb),
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
      "de.sciss.patterns.lucre.impl", 
      "de.sciss.patterns.stream", 
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
    "-doc-title", s"${baseName} ${PROJECT_VERSION} API",
    "-Ymacro-no-expand"
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
    // git.gitCurrentBranch           := "master",
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

