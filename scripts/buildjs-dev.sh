#!/bin/bash

cd ./js

echo 'Ajv = require("ajv")' > webapi-parser-dev.js
cat ./target/artifact/webapi-parser-module.js >> webapi-parser-dev.js
chmod a+x webapi-parser-dev.js
mv webapi-parser-dev.js ./module/webapi-parser-dev.js

cd ../..