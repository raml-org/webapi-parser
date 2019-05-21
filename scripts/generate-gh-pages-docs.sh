#!/bin/bash

# Generates:
#   - JS API docs HTML (/webapi-parser/docs/js/index.html)
#   - WebApi Model docs HTML (/webapi-parser/docs/js)
#   - Java API docs HTML (/webapi-parser/docs/java)
#   - Tutorials wrapped in Gitbook (/webapi-parser/docs/index.html)
#
# NOTES:
#   - Call this script from the root of webapi-parser project;
#
# To publish these docs to gh-pages branch:
#   1. Run this script;
#   2. Commit and push;


# Build java and js docs
./scripts/generate-java-docs.sh
./scripts/generate-js-docs.sh


cd docs

# Copy repo README.md
cp ../README.md .

# Install gitbook and build docs
npm install gitbook-cli
./node_modules/gitbook-cli/bin/gitbook.js build

# Remove unnecessary duplicate folders
rm -rf _book/js/ _book/java/ _book/node_modules/

# Copy generated gitbook html to docs root
cp -R _book/* .

# Remove generated directory
rm -rf _book
