#!/bin/bash

# Generates:
#   - JS API docs (/webapi-parser/docs/js)
#   - WebApi Model docs (/webapi-parser/docs/js)
#
# NOTES:
#   - Call this script from the root of webapi-parser project;
#   - Option --ignoreCompilerErrors is used in typedoc command
#     to ignore incorrect typings of dependencies;
#   - Because of this issue: https://github.com/TypeStrong/typedoc/issues/319
#     it's currently only possible to include or exclude ALL
#     dependencies from typedoc generation;

mkdir -p docs/js/tmp_module/node_modules

echo "Generating JS docs"
cp ./js/module/typings/webapi-parser.d.ts ./docs/js/tmp_module/webapi-parser.d.ts

cd ./docs/js/tmp_module

npm init -y
npm install -g typedoc@0.15.4

curl https://raw.githubusercontent.com/aml-org/amf/master/amf-client/js/typings/amf-client-js.d.ts -o amf-client-js.d.ts
typedoc --out ./gendocs ./webapi-parser.d.ts ./amf-client-js.d.ts \
        --includeDeclarations \
        --mode file \
        --ignoreCompilerErrors \
        --readme none \
        --theme minimal \
        --name "webapi-parser" \
        --excludeExternals

cd ..
cp -r ./tmp_module/gendocs/* ./
rm -rf ./tmp_module
