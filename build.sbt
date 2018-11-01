import org.scalajs.core.tools.linker.ModuleKind
import sbt.Keys.{libraryDependencies, resolvers}
import sbtcrossproject.CrossPlugin.autoImport.crossProject
val ivyLocal = Resolver.file("ivy", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns)

name := "webapi-parser"

version in ThisBuild := "0.0.1"

publish := {}
parallelExecution in ThisBuild := false

jsEnv := new org.scalajs.jsenv.nodejs.NodeJSEnv()

val settings = Common.settings ++ Common.publish ++ Seq(
  organization := "com.github.ramlorg",
  resolvers ++= List(ivyLocal, Common.releases, Common.snapshots, Resolver.mavenLocal),
  resolvers += "jitpack" at "https://jitpack.io",
  credentials ++= Common.credentials(),
  aggregate in assembly := false,
  libraryDependencies ++= Seq(
    "com.github.amlorg" %%% "amf-client" % "2.0.0",
    "org.scalatest"     %%% "scalatest" % "3.0.5" % Test,
    "com.github.scopt"  %%% "scopt"     % "3.7.0"
  )
)

/** **********************************************
  * WebAPI-Parser
  ********************************************** */
lazy val webapi = crossProject(JSPlatform, JVMPlatform)
  .settings(name := "webapi-parser")
  .in(file("./webapi-parser"))
  .settings(settings ++ (parallelExecution in ThisBuild := false) : _*)
  .jvmSettings(
    libraryDependencies += "org.scala-js"           %% "scalajs-stubs"          % scalaJSVersion % "provided",
    libraryDependencies += "org.scala-lang.modules" % "scala-java8-compat_2.12" % "0.8.0",
    libraryDependencies += "org.json4s"             %% "json4s-native"         % "3.5.4",
    libraryDependencies += "com.github.everit-org.json-schema" % "org.everit.json.schema" % "1.9.2",
    artifactPath in (Compile, packageDoc) := baseDirectory.value / "target" / "artifact" / "webapi-parser-javadoc.jar"
  )
  .jsSettings(
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.2",
    scalaJSModuleKind := ModuleKind.CommonJSModule,
    artifactPath in (Compile, fastOptJS) := baseDirectory.value / "target" / "artifact" / "webapi-parser-module.js"
  )

lazy val webapiJVM = webapi.jvm.in(file("./webapi-parser/jvm"))
lazy val webapiJS  = webapi.js.in(file("./webapi-parser/js"))

// Tasks

val buildJS = TaskKey[Unit](
  "buildJS",
  "Build npm module")
buildJS := {
  val _ = (fastOptJS in Compile in webapiJS).value
  // "./amf-client/js/build-scripts/buildjs.sh".!
}

addCommandAlias(
  "buildCommandLine",
  "; clean; clientJVM/assembly"
)
