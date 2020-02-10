---
---

# Migration guide (JS)
Welcome!

As you may already know RAML 1.0 JS parser `raml-1-parser` has been deprecated in favor of the new and better one - `webapi-parser`. This guide describes how to migrate an existing code from `raml-1-parser` to `webapi-parser`.

Migration process consists of these steps:
1. [Considering parsers differences](#understanding-parsers-differences)
2. Installing the new parser (as described in respective [readme section](https://github.com/raml-org/webapi-parser/tree/develop#javascript))
3. [Migrating the code](#migrating-the-code)

## Considering parsers differences
There are few differences to consider when migrating to `webapi-parser`:
* In addition to RAML 1.0 parsing it can also resolve (flatten) it and validate. The parser also supports a number of other API Spec formats: RAML 0.8, OAS 2.0, OAS 3.0, AMF JSON-LD;
* `webapi-parser` provides only async/Promise-based API;
* API of the model/object it produces on parsing is completely different from the one produced by `raml-1-parser`. You can research the new model API by following the link in the [assistance section](#need-assistance) below.

## Migrating the code
Consider this code which uses `raml-1-parser`:
```js
const lib = require('raml-1-parser')

async someFunc () {

}

someFunc()
```

## Need Assistance?
Here are the things to do if you have more questions:
* Check out more of our [guides](https://raml-org.github.io/webapi-parser/)
* Explore relevant [examples](https://github.com/raml-org/webapi-parser/tree/master/examples/js)
* Research the API with the [developer documentation](https://raml-org.github.io/webapi-parser/js/modules/webapiparser.html)
* Ask your question at [github issues](https://github.com/raml-org/webapi-parser/issues)
