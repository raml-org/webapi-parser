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
# ./scripts/generate-java-docs.sh
# ./scripts/generate-js-docs.sh


cd docs

# Install gitbook
npm init -y
npm install gitbook-cli
mkdir -p ./node_modules/gitbook-cli/.gitbook
export GITBOOK_DIR="./node_modules/gitbook-cli/.gitbook"

# Build docs
./node_modules/gitbook-cli/bin/gitbook.js build

# Remove unnecessary duplicate folders
rm -rf _book/js/ _book/java/ _book/node_modules/

# Copy generated gitbook html to docs root
cp -R _book/* .

# Remove generated directory
rm -rf _book
