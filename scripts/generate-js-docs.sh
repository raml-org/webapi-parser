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

echo "Generating JS docs"

npm install -g typedoc@0.15.0

mkdir -p docs/js/tmp_module
cd ./docs/js/tmp_module

echo "Merging AMF and webapi-parser declaration files"
curl https://raw.githubusercontent.com/aml-org/amf/master/amf-client/js/typings/amf-client-js.d.ts -o amf.d.ts
tail -n +2 ../../../js/module/typings/webapi-parser.d.ts > merged.d.ts
echo '' >> merged.d.ts
echo 'declare module "webapi-parser" {' >> merged.d.ts
tail -n +2 amf.d.ts >> merged.d.ts

typedoc --out ./gendocs ./merged.d.ts \
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
