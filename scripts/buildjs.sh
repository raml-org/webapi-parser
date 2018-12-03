#!/bin/bash

cd ./js

echo 'Ajv = require("ajv")' > webapi-parser.js
cat ./target/artifact/webapi-parser-module.js >> webapi-parser.js
chmod a+x webapi-parser.js
mv webapi-parser.js ./module/webapi-parser.js

cd ../..