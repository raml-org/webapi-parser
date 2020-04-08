#!/usr/bin/env bash

# NOTES:
# - call this script from the root of webapi-parser project
# - it will then work on the first build.sbt found anywhere in the project path
# - it assumes project version setting is a literal e.g.: `ThisBuild / version := "0.1-SNAPSHOT"`
# - it requires environment variable from .npmrc.template to be set

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

echo "Running fastOptJS"
sbt webapiJS/fastOptJS
echo "Finished fastOptJS"

echo "Running buildjs-dev script"
./scripts/buildjs-dev.sh
echo "Finished buildjs-dev script"

cp README.md js/module
cd js/module

# Create per-project NPM config file with credentials
echo "always-auth=true" > .npmrc
echo "//registry.npmjs.org/:_authToken=\${NPM_API_TOKEN}" >> .npmrc

if $IS_BETA; then
    LATEST_BETA=`npm v webapi-parser dist-tags.beta`

    # If beta was previously published, parse its number and
    # increment it by 1. Otherwise make new beta
    # release "*-beta.0".
    # Assume beta versions are numbered like 1.1.1-beta.7
    if [[ $LATEST_BETA == ${PROJECT_VERSION}* ]]; then
        parts=(${LATEST_BETA//./ })
        OLD_PRERELEASE_NUM=${parts[-1]}
        PRERELEASE_NUM=$(($OLD_PRERELEASE_NUM + 1))
    else
        PRERELEASE_NUM="0"
    fi

    PROJECT_VERSION="${PROJECT_VERSION}-beta.${PRERELEASE_NUM}"

    echo "New beta release $PROJECT_VERSION"
    npm version ${PROJECT_VERSION} --force --no-git-tag-version

    echo "Publish new beta release"
    npm publish --tag beta

else
    EXISTING_RELEASE=`npm view webapi-parser@$PROJECT_VERSION version`

    if ! [[ $EXISTING_RELEASE == "" ]]; then
        echo "Release $PROJECT_VERSION already exists"
        exit 0
    fi

    echo "New release $PROJECT_VERSION"
    npm version ${PROJECT_VERSION} --force --no-git-tag-version

    echo "Publish new release"
    npm publish
fi

git checkout package.json

cd ../..
