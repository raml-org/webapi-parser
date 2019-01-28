import sbt.Keys.{scalacOptions, _}
import sbt.{Def, _}

object Common {

  val settings: Seq[Def.Setting[_]] = Seq(
    scalaVersion := "2.12.6",
    parallelExecution in Test := false,
    fork in Test := false,
    scalacOptions ++= Seq("-unchecked" /*, "-deprecation", "-Xfatal-warnings" */ ),
    scalacOptions ++= Seq("-encoding", "utf-8")
  )

  val publish: Seq[Def.Setting[_]] = Seq(
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
      else Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    // POM settings for Sonatype
    homepage := Some(url("https://github.com/raml-org/webapi-parser")),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/raml-org/webapi-parser"),
        "scm:git@github.com:raml-org/webapi-parser.git"
      )
    ),
    developers := List(
      Developer(
        "raml-org",
        "Raml Org",
        "user@raml.org",
        url("https://github.com/raml-org")
      )
    ),
    licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0")),
    publishMavenStyle := true,
    publishConfiguration ~= { config =>
      val newArts = config.artifacts.filterKeys(_.`type` != Artifact.SourceType)
      new PublishConfiguration(config.ivyFile,
                               config.resolverName,
                               newArts,
                               config.checksums,
                               config.logging,
                               overwrite = true)
    }
  )

  def credentials(): Seq[Credentials] = {
    val cs =
      Seq("SONATYPE_USERNAME" -> "SONATYPE_PASSWORD")
        .flatMap({
          case (user, password) =>
            for {
              u <- sys.env.get(user)
              p <- sys.env.get(password)
            } yield u -> p
        })
    println("Using credentials from environment variables ")
    cs.flatMap({
      case (user, password) =>
        Seq(
          Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", user, password)
        )
    })
  }
}
