// Profile name of the sonatype account. The default is the same with the organization value
sonatypeProfileName := "org.raml"

// To sync with Maven central, this must be true:
publishMavenStyle := true

// License of your choice
licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))

// Where is the source code hosted
homepage := Some(url("https://github.com/raml-org/webapi-parser"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/raml-org/webapi-parser"),
    "scm:git@github.com:raml-org/webapi-parser.git"
  )
)
developers := List(
  Developer(
    "raml-org",
    "Raml Org",
    "info@raml.org",
    url("https://github.com/raml-org")
  )
)
