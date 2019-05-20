#!/usr/bin/env bash

# Valid version to tag pairs examples:
#     0.0.2-SNAPSHOT -> v0.0.2-beta
#     0.0.3          -> v0.0.3

PROJECT_VERSION=`find '.' -name "build.sbt" |
    head -n1 |
    xargs grep '[ \t]*ThisBuild / version :=' |
    head -n1 |
    sed 's/.*"\(.*\)".*/\1/' 2> /dev/null`

CONVERTED_VERSION="${PROJECT_VERSION/-SNAPSHOT/-beta}"

if ! [[ $TRAVIS_TAG =~ ^v${CONVERTED_VERSION}$ ]]; then
    echo "Github tag doesn't match version in build.sbt. Expected tag: v${CONVERTED_VERSION}"
    exit 1
fi
