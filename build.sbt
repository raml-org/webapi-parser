import org.scalajs.core.tools.linker.ModuleKind
import sbt.Keys.{libraryDependencies, resolvers}
import sbtcrossproject.CrossPlugin.autoImport.crossProject
val ivyLocal = Resolver.file("ivy", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)

name := "webapi-parser"

version in ThisBuild := "0.0.1"

publish := {}

jsEnv := new org.scalajs.jsenv.nodejs.NodeJSEnv()

val settings = Common.settings ++ Common.publish ++ Seq(
  organization := "com.github.amlorg",
  resolvers ++= List(ivyLocal, Resolver.mavenLocal),
  resolvers += "jitpack" at "https://jitpack.io",
  aggregate in assembly := false,
  pgpPassphrase := sys.env.get("GPG_PASSPHRASE").map(_.toArray),
  credentials ++= Common.credentials(),
  libraryDependencies ++= Seq(
    "com.github.amlorg" %%% "amf-webapi" % "3.0.0",
    "com.github.amlorg" %%% "amf-validation" % "3.0.0"
  )
)

lazy val Javadoc = config("genjavadoc") extend Compile

lazy val javadocSettings = inConfig(Javadoc)(Defaults.configSettings) ++ Seq(
  scalaVersion := "2.12.6",
  addCompilerPlugin("com.typesafe.genjavadoc" %% "genjavadoc-plugin" % "0.11" cross CrossVersion.full),
  scalacOptions += s"-P:genjavadoc:out=${target.value}/java",
  packageDoc in Compile := (packageDoc in Javadoc).value,
  sources in Javadoc :=
    (target.value / "java" ** "*.java").get ++
    (sources in Compile).value.filter(_.getName.endsWith(".java")),
  javacOptions in Javadoc := Seq(),
  artifactName in packageDoc in Javadoc := ((sv, mod, art) =>
    "" + mod.name + "_" + sv.binary + "-" + mod.revision + "-javadoc.jar")
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
    libraryDependencies += "org.scala-js"           %% "scalajs-stubs"          % scalaJSVersion % "provided",
    aggregate in assembly := true,
    test in assembly := {},
    assemblyOutputPath in assembly := file(s"./webapi-parser-${version.value}.jar"),
    assemblyMergeStrategy in assembly := {
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
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
    }
  )
  .jsSettings(
    scalaJSModuleKind := ModuleKind.CommonJSModule,
    artifactPath in (Compile, fullOptJS) := baseDirectory.value / "target" / "artifact" / "webapi-parser-module.js"
  )

lazy val webapiJVM = webapi.jvm.in(file("./jvm"))
lazy val webapiJS  = webapi.js.in(file("./js"))

// Tasks

val buildJS = TaskKey[Unit](
  "buildJS",
  "Build npm module")

buildJS := {
  val _ = (fullOptJS in Compile in webapiJS).value
  "./build-scripts/buildjs.sh".!
}

// Assemble .jar containing all dependencies. Can be used as local jar dependency in
// another projects.
addCommandAlias(
  "assembleFatJar",
  "; clean; webapiJVM/assembly"
)

// Generate Java source from Scala source and javadocs for them. Uses "genjavadoc".
addCommandAlias(
  "generateJavadocs",
  "; webapiJVM/genjavadoc:doc; webapiJVM/packageDoc"
)
