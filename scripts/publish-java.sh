#!/usr/bin/env bash

# NOTES:
# - call this script from the root of webapi-parser project

# Exit on unset variables or if any of the following commands returns non-zero
set -eu

./scripts/validate-git-tag.sh


# Find the first build.sbt in the path tree of this script,
# find the version line and extract the content within double quotes
PROJECT_VERSION=`find '.' -name "build.sbt" |
    head -n1 |
    xargs grep '[ \t]*ThisBuild / version :=' |
    head -n1 |
    sed 's/.*"\(.*\)".*/\1/' 2> /dev/null`

if [[ ${PROJECT_VERSION} == *-SNAPSHOT ]]; then
    IS_BETA=true
else
    IS_BETA=false
fi


# Make sure non-snapshot release with the same version does not exist
if ! $IS_BETA; then
    # Check if artifact already exists by trying to download its POM file
    # (https://search.maven.org/classic/#api)
    URL="https://search.maven.org/remotecontent?filepath=org/raml/webapi-parser/$PROJECT_VERSION/webapi-parser-$PROJECT_VERSION.pom"
    if curl --output /dev/null --silent --fail -r 0-0 "$URL"; then
        echo "Release $PROJECT_VERSION already exists"
        exit 0
    fi
fi


./scripts/generate-java-docs.sh

echo "Packaging artifact"
sbt webapiJVM/package

echo "Uploading artifact to Sonatype staging"
sbt webapiJVM/publishSigned

# If the project version has "SNAPSHOT" suffix, the project will be
# published to the snapshot repository of Sonatype, and we cannot use
# sonatypeRelease (and sonatypeReleaseAll) command.
if ! $IS_BETA; then
    echo "Promoting the release to be ready for synching to Maven Central"
    sbt sonatypeReleaseAll
fi
