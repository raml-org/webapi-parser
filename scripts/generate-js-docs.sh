#!/bin/bash

# Generates:
#   - JS API docs (/webapi-parser/docs/js)
#   - AMF Model docs (/webapi-parser/docs/js)
#
# NOTES:
#   - Call this script from the root of webapi-parser project;
#   - Option --ignoreCompilerErrors is used in typedoc command
#     to ignore incorrect typings of dependencies;
#   - Because of this issue: https://github.com/TypeStrong/typedoc/issues/319
#     it's currently only possible to include or exclude ALL
#     dependencies from typedoc generation;

mkdir -p docs/js/tmp_module/node_modules

# JS Docs
echo "Generating JS docs"
cp ./js/module/typings/webapi-parser.d.ts ./docs/js/tmp_module/webapi-parser.d.ts

cd ./docs/js/tmp_module

npm init -y
npm install -g typedoc@0.13.0
npm install amf-client-js

mkdir -p ./node_modules/@types/amf-client-js/
cp ./node_modules/amf-client-js/typings/amf-client-js.d.ts ./node_modules/@types/amf-client-js/amf-client-js.d.ts

typedoc --out ./webapi-parser ./webapi-parser.d.ts \
        --includeDeclarations \
        --mode file \
        --ignoreCompilerErrors \
        --readme none \
        --theme minimal \
        --name "webapi-parser" \
        --excludeExternals

typedoc --out ./amf-client-js ./node_modules/amf-client-js/typings/amf-client-js.d.ts \
        --includeDeclarations \
        --mode file \
        --ignoreCompilerErrors \
        --readme none \
        --theme minimal \
        --name "amf-client-js" \
        --excludeExternals

cd ..
cp -r ./tmp_module/webapi-parser ./
cp -r ./tmp_module/amf-client-js ./
rm -rf ./tmp_module
