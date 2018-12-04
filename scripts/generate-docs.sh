#!/bin/bash

# Generates:
#   - JS API docs (/webapi-parser/docs/js)
#   - Java API docs (/webapi-parser/docs/java)
#   - AMF Model docs (/webapi-parser/docs/js)
#
# NOTES:
#   - Call this script from the root of webapi-parser project;
#   - Option --ignoreCompilerErrors is used in typedoc command
#     to ignore incorrect typings of dependencies;
#   - Because of this issue: https://github.com/TypeStrong/typedoc/issues/319
#     it's currently only possible to include or exclude ALL
#     dependencies from typedoc generation;

PROJROOT="$(cd "$(dirname "$0")/.." && pwd)"
TMPMODULE="${PROJROOT}/docs/js/tmp_module"

mkdir -p $PROJROOT/docs/java
mkdir -p $TMPMODULE


# # Java Docs
echo "Generating Java docs"
sbt generateJavadocs
cp -r $PROJROOT/jvm/target/scala-2.12/genjavadoc-api/* $PROJROOT/docs/java


# JS Docs
echo "Generating JS docs"
cp $PROJROOT/js/module/typings/webapi-parser.d.ts $PROJROOT/docs/js/tmp_module/webapi-parser.d.ts

cd $TMPMODULE

npm install -g typedoc@0.13.0
npm install amf-client-js

mkdir -p $TMPMODULE/node_modules/@types/amf-client-js/
cp $TMPMODULE/node_modules/amf-client-js/typings/amf-client-js.d.ts $TMPMODULE/node_modules/@types/amf-client-js/amf-client-js.d.ts
typedoc --out $TMPMODULE/gendocs $TMPMODULE/webapi-parser.d.ts --includeDeclarations --mode file --ignoreCompilerErrors

cd ..
cp -r $TMPMODULE/gendocs/* .
rm -rf $TMPMODULE
