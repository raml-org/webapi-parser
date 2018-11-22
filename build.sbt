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
  resolvers ++= List(ivyLocal, Common.releases, Common.snapshots, Resolver.mavenLocal),
  resolvers += "jitpack" at "https://jitpack.io",
  aggregate in assembly := false,
  credentials ++= Common.credentials(),
  libraryDependencies ++= Seq(
    "com.github.amlorg" %%% "amf-webapi" % "3.0.0",
    "com.github.amlorg" %%% "amf-validation" % "3.0.0"
  )
)

/** **********************************************
  * WebAPI-Parser
  ********************************************** */
lazy val webapi = crossProject(JSPlatform, JVMPlatform)
  .settings(name := "webapi-parser")
  .in(file("./"))
  .settings(settings)
  .jvmSettings(
    libraryDependencies += "org.scala-js"           %% "scalajs-stubs"          % scalaJSVersion % "provided",
    artifactPath in (Compile, packageDoc) := baseDirectory.value / "target" / "artifact" / "webapi-parser-javadoc.jar",
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
    artifactPath in (Compile, fastOptJS) := baseDirectory.value / "target" / "artifact" / "webapi-parser-module.js"
  )

lazy val webapiJVM = webapi.jvm.in(file("./jvm"))
lazy val webapiJS  = webapi.js.in(file("./js"))

// Tasks

val buildJS = TaskKey[Unit](
  "buildJS",
  "Build npm module")

buildJS := {
  val _ = (fastOptJS in Compile in webapiJS).value
  "./build-scripts/buildjs.sh".!
}


// Assemble .jar containing all dependencies. Can be used as local jar dependency in
// another projects.
addCommandAlias(
  "assembleFatJar",
  "; clean; webapiJVM/assembly"
)
