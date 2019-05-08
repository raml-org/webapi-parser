#!/usr/bin/env bash

# NOTES:
# - call this script from the root of webapi-parser project
# - it will then work on the first build.sbt found anywhere in the project path
# - it assumes project version setting is a literal e.g.: `version in ThisBuild := "0.1-SNAPSHOT"`
# - it requires environment variable from .npmrc.template to be set

# Exit on unset variables or if any of the following commands returns non-zero
set -eu

# Find the first build.sbt in the path tree of this script,
# find the version line and extract the content within double quotes
PROJECT_VERSION=`find '.' -name "build.sbt" |
    head -n1 |
    xargs grep '[ \t]*version in ThisBuild :=' |
    head -n1 |
    sed 's/.*"\(.*\)".*/\1/' 2> /dev/null`

if [[ ${PROJECT_VERSION} == *-SNAPSHOT ]]; then
    IS_BETA=true
    PROJECT_VERSION=${PROJECT_VERSION%"-SNAPSHOT"}
else
    IS_BETA=false
fi

echo "Build.sbt version: $PROJECT_VERSION"
echo "Is beta: $IS_BETA"

echo "Running fullOptJS"
sbt webapiJS/fullOptJS
echo "Finished fullOptJS"

echo "Running buildjs script"
./scripts/buildjs.sh
echo "Finished buildjs script"

cd js/module

# Create per-project NPM config file with credentials
echo "always-auth=true" > .npmrc
echo "//registry.npmjs.org/:_authToken=\${NPM_API_TOKEN}" >> .npmrc

if $IS_BETA; then
    LATEST_BETA=`npm v webapi-parser dist-tags.beta`

    if [[ $LATEST_BETA == ${PROJECT_VERSION}* ]]; then
        # Assume beta versions are numbered like 1.1.1-beta.7
        parts=(${LATEST_BETA//./ })
        OLD_PRERELEASE_NUM=${arrFoo[-1]}
        PRERELEASE_VERSION=$(($OLD_PRERELEASE_NUM + 1))
    else
        PRERELEASE_NUM="0"
    fi

    npm version "${PROJECT_VERSION}-beta.${PRERELEASE_NUM}" --force --no-git-tag-version
    npm publish --tag beta

else
    LATEST_RELEASE=`npm v webapi-parser dist-tags.latest`

    if [[ $PROJECT_VERSION == $LATEST_RELEASE ]]; then
        echo "Latest release is already $PROJECT_VERSION"
        exit 1
    fi

    echo "New release $PROJECT_VERSION"
    npm version ${PROJECT_VERSION} --force --no-git-tag-version

    echo "Publish new release"
    npm publish
fi

# Push package json with a new version
git add package.json
git commit -m "Bump version in package.json to $PROJECT_VERSION"
git push

cd ../..
