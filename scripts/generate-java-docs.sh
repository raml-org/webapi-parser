#!/bin/bash

# Generates:
#   - Java API docs HTML (/webapi-parser/docs/java)
#   - Java API -javadoc.jar file
#
# NOTES:
#   - Call this script from the root of webapi-parser project;
#
# To publish these docs to gh-pages branch:
#   1. Run this script;
#   2. Replace directory "java" in the "gh-pages" branch root
#      with generated "docs/java" directory;
#   3. Commit and push;

mkdir -p docs/java

echo "Generating Java docs"
sbt clean
sbt webapiJVM/genjavadoc:doc
rm ./jvm/target/java/webapi/*$.java
sbt webapiJVM/packageDoc
cp -r ./jvm/target/scala-2.12/genjavadoc-api/* ./docs/java
