#!/usr/bin/env bash

# NOTES:
# - call this script from the root of webapi-parser project
# - it will then work on the first build.sbt found anywhere in the project path
# - it assumes project version setting is a literal e.g.: `version in ThisBuild := "0.1-SNAPSHOT"`
# - it requires environment variable from .npmrc.template to be set

# Exit on unset variables or if any of the following commands returns non-zero
#set -eu

# Find the first build.sbt in the path tree of this script,
# find the version line and extract the content within double quotes
PROJECT_VERSION=`find '.' -name "build.sbt" |
    head -n1 |
    xargs grep '[ \t]*version in ThisBuild :=' |
    head -n1 |
    sed 's/.*"\(.*\)".*/\1/' 2> /dev/null`

if [[ ${PROJECT_VERSION} == *-SNAPSHOT ]]; then
    IS_SNAPSHOT=true
else
    IS_SNAPSHOT=false
fi

echo "Build.sbt version: $PROJECT_VERSION"
echo "Is snapshot: $IS_SNAPSHOT"

echo "Running fullOptJS"
sbt webapiJS/fullOptJS
echo "Finished fullOptJS"

echo "Running buildjs script"
./buildjs.sh
echo "Finished buildjs script"

cd js/module

# Copy NPM auth credentials
cp .npmrc.template $HOME/.npmrc

if $IS_SNAPSHOT; then
    LATEST_SNAPSHOT=`npm v webapi-parser dist-tags.snapshot`

    echo "Repo latest snapshot: $LATEST_SNAPSHOT"

    if [[ $LATEST_SNAPSHOT == ${PROJECT_VERSION}* ]]; then
        echo "Just add one prerelease"
        npm version ${LATEST_SNAPSHOT} --force --no-git-tag-version
        npm version prerelease --force --no-git-tag-version

        echo "Publish one more snapshot"
    else
        echo "Start prerelease from scratch"
        npm version ${PROJECT_VERSION} --force --no-git-tag-version
        npm version prerelease --force --no-git-tag-version

        echo "Publish new snapshot"
    fi

    npm publish --tag snapshot

    echo "Finished snapshot publish"
    echo "Add 'beta' tag to snapshot"

    NEW_VERSION=`node -p "require('./package.json').version"`

    echo "To version: $NEW_VERSION"

    npm dist-tag add webapi-parser@${NEW_VERSION} beta
else
    LATEST_RELEASE=`npm v webapi-parser dist-tags.latest`
    if [[ $PROJECT_VERSION != $LATEST_RELEASE ]]; then
        echo "New release $PROJECT_VERSION"
        npm version ${PROJECT_VERSION} --force --no-git-tag-version

        echo "Publish new release"
    else
        echo "Latest release is already $PROJECT_VERSION"
    fi

    npm publish

    echo "Finished latest publish"
    echo "Add 'release' tag to latest"

    npm dist-tag add webapi-parser@${PROJECT_VERSION} release

fi

# Reset package.json so that the new version is not pushed
git checkout package.json

cd ../..