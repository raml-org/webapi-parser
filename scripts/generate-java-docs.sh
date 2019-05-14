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
#   2. Commit and push;

mkdir -p docs/java

echo "Cleaning environment and compiling Java classes"
sbt clean
sbt webapiJVM/compile

echo "Generating Java docs"
sbt webapiJVM/genjavadoc:doc
rm ./jvm/target/java/webapi/*$.java
rm ./jvm/target/java/webapi/*Test.java
sbt webapiJVM/packageDoc
cp -r ./jvm/target/genjavadoc-api/* ./docs/java
