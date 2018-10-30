import sbt._

object MyBuild extends Build {
  lazy val root = Project("root", file(".")) dependsOn(amfProject)
  lazy val amfProject = RootProject(uri("git://github.com/aml-org/amf.git"))
}
