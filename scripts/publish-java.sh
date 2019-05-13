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

# Find the first build.sbt in the path tree of this script,
# find the version line and extract the content within double quotes
PROJECT_VERSION=`find '.' -name "build.sbt" |
    head -n1 |
    xargs grep '[ \t]*ThisBuild / version :=' |
    head -n1 |
    sed 's/.*"\(.*\)".*/\1/' 2> /dev/null`

# If the project version has "SNAPSHOT" suffix, the project will be
# published to the snapshot repository of Sonatype, and we cannot use
# sonatypeRelease command.
if ! [[ ${PROJECT_VERSION} == *-SNAPSHOT ]]; then
    echo "Promoting the release to be ready for synching to Maven Central"
    sbt sonatypeRelease
fi
