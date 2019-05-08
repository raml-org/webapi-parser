#!/usr/bin/env bash

# NOTES:
# - call this script from the root of webapi-parser project

# Exit on unset variables or if any of the following commands returns non-zero
set -eu

./scripts/generate-java-docs.sh

echo "Packaging artifact"
sbt webapiJVM/package

echo "Uploading artifact to Sonatype staging"
sbt webapiJVM/publishSigned

echo "Promoting the release to be ready for synching to Maven Central"
sbt webapiJVM/sonatypeRelease
