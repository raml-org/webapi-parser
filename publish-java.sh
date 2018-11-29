#!/usr/bin/env bash

echo "Generating JavaDocs"
sbt generateJavadocs

echo "Packaging artifact"
sbt webapiJVM/package

echo "Uploading artifact to Sonatype staging"
sbt publishSigned

echo "Promoting the release to be ready for synching to Maven Central"
sbt sonatypeRelease
