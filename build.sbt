import org.scalajs.core.tools.linker.ModuleKind
import sbt.Keys.{libraryDependencies, resolvers}
import sbtcrossproject.CrossPlugin.autoImport.crossProject
val ivyLocal = Resolver.file("ivy", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)

name := "webapi-parser"

ThisBuild / version := "0.2.0"

publish := {}

jsEnv := new org.scalajs.jsenv.nodejs.NodeJSEnv()

val muleNexus = "https://repository-master.mulesoft.org/nexus/content/repositories"

val settings = Common.settings ++ Common.publish ++ Seq(
  organization := "org.raml",
  resolvers ++= List(ivyLocal, Resolver.mavenLocal),
  resolvers += "MuleSoftSnapshots" at s"$muleNexus/snapshots",
  resolvers += "MuleSoftReleases" at s"$muleNexus/releases",
  resolvers += "jitpack" at "https://jitpack.io",
  assembly / aggregate := false,
  Compile / publishArtifact := true,
  Test / publishArtifact := false,
  useGpg := true,
  useGpgAgent := false,
  pgpPassphrase := sys.env.get("GPG_PASSPHRASE").map(_.toArray),
  credentials ++= Common.credentials(),
  libraryDependencies ++= Seq(
    "org.scalatest"     %%% "scalatest"       % "3.0.5" % "test",
    "com.github.amlorg" %%% "amf-webapi"      % "3.2.2",
    "com.github.amlorg" %%% "amf-validation"  % "3.2.2"
  )
)

lazy val Javadoc = config("genjavadoc") extend Compile

lazy val javadocSettings = inConfig(Javadoc)(Defaults.configSettings) ++ Seq(
  scalaVersion := "2.12.6",
  addCompilerPlugin("com.typesafe.genjavadoc" %% "genjavadoc-plugin" % "0.13" cross CrossVersion.full),
  scalacOptions += s"-P:genjavadoc:out=${target.value}/java",
  scalacOptions += s"-P:genjavadoc:suppressSynthetic=true",
  Compile / packageDoc := (Javadoc/ packageDoc).value,
  Javadoc / sources :=
    (target.value / "java" ** "*.java").get ++
    (Compile / sources).value.filter(_.getName.endsWith(".java")),
  Javadoc / javacOptions := Seq(),
  Javadoc / packageDoc / artifactName:= ((sv, mod, art) =>
    "" + mod.name + "-" + mod.revision + "-javadoc.jar")
)

/** **********************************************
  * WebAPI-Parser
  ********************************************** */
lazy val webapi = crossProject(JSPlatform, JVMPlatform)
  .settings(name := "webapi-parser")
  .in(file("./"))
  .settings(settings)
  .configs(Javadoc)
  .settings(javadocSettings: _*)
  .jvmSettings(
    libraryDependencies += "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided",
    assembly / aggregate := true,
    assembly / test := {},
    assembly / assemblyOutputPath := file(s"./webapi-parser-${version.value}.jar"),
    assembly / assemblyMergeStrategy := {
      case x if x.toString.contains("commons/logging") => {
        MergeStrategy.discard
      }
      case x if x.toString.endsWith("JS_DEPENDENCIES") => {
        MergeStrategy.discard
      }
      case PathList(ps @ _*) if ps.last endsWith "JS_DEPENDENCIES" => {
        MergeStrategy.discard
      }
      case x =>
        val oldStrategy = (assembly / assemblyMergeStrategy).value
        oldStrategy(x)
    }
  )
  .jsSettings(
    scalaJSModuleKind := ModuleKind.CommonJSModule,
    Compile / fullOptJS / artifactPath := baseDirectory.value / "target" / "artifact" / "webapi-parser-module.js"
  )

lazy val webapiJVM = webapi.jvm.in(file("./jvm"))
lazy val webapiJS  = webapi.js.in(file("./js"))

// Assemble .jar containing all dependencies. Can be used as local jar dependency in
// another projects.
addCommandAlias(
  "assembleFatJar",
  "; clean; webapiJVM/assembly"
)
