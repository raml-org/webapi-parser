---
---

# Migration guide (JS)
Welcome!

As you may already know RAML 0.8/1.0 JS parser `raml-1-parser` has been deprecated in favor of the new and better one - `webapi-parser`. This guide describes how to migrate an existing code from `raml-1-parser` to `webapi-parser`.

Migration process consists of following steps:
1. [Considering parsers differences](#considering-parsers-differences)
2. Installing the new parser (as described in respective [readme section](https://github.com/raml-org/webapi-parser#javascript))
3. [Migrating the code](#migrating-the-code)

## Considering parsers differences
There are few differences to consider when migrating to `webapi-parser`:
* In addition to RAML 0.8 and RAML 1.0 parsing it can also resolve (flatten) it and validate. The parser also supports a number of other API Spec formats: OAS 2.0, OAS 3.0, AMF JSON-LD;
* `webapi-parser` provides only async/Promise-based API;
* API of the model/object it produces on parsing is completely different from the one produced by `raml-1-parser`. You can research the new model API by following the link in the [assistance section](#need-assistance) below.

## Migrating the code
Consider this code which uses `raml-1-parser`:
```js
const parser = require('raml-1-parser')

async someFunc () {
  const api = await parser.loadRAML('/path/to/raml10/api.raml')
  const expanded = api.expand(true)
  // Do something with the expanded API
}

someFunc()
```

Here's how it can be reworked to use `webapi-parser`:
```js
const wap = require('webapi-parser').WebApiParser

async someFunc () {
  // Or wap.raml08 for RAML 0.8 operations
  const api = await wap.raml10.parse('file:///path/to/raml10/api.raml')
  const expanded = await wap.raml10.resolve(api)
  // Do something with the expanded API
}

someFunc()
```

In the example above, object `WebApiParser` contains namespaces for all the supported API Spec formats: `raml10`. `raml08`, `oas20`, `oas30`, `amfGraph`, each having an identical interface (OAS namespaces have an extra few methods). The list of supported operations each format supports includes parsing, resolution(flattening), validation and string generation.

To get a description of each namespace and operation please research the new model API by following the link in the [assistance section](#need-assistance) below.

## Detailed migration examples
This section lists migration examples of the most common `raml-1-parser` parsing and model methods. Snippets are separated with a newline. First line of each example shows `raml-1-parser` method usage, while the second line shows how to achieve the same functionality with `webapi-parser` if possible. If no obvious alternative exists, a comment gives more detail.

### Parsers
```js
const oldParser = require('raml-1-parser')
const wp = require('webapi-parser')
const wap = wp.WebApiParser

const node = await oldParser.loadApi('/path/to/api.raml')
const model = await wap.raml10.parse('file:///path/to/api.raml')
// or
// const model = wap.raml08.parse('file:///path/to/api.raml')

oldParser.asFragment(node)
// Not necessary. Webapi-parser parses fragments into different model types.

oldParser.isFragment(node)
!(model instanceof wp.webapi.WebApiDocument)

oldParser.loadApiSync('/path/to/api.raml')
// Not supported

oldParser.loadRAML('/path/to/api.raml')
wap.raml10.parse('file:///path/to/api.raml')
wap.raml08.parse('file:///path/to/api.raml')

oldParser.loadRAMLSync('/path/to/api.raml')
// Not supported

oldParser.parseRAML('#%RAML 1.0\n...')
wap.raml10.parse('#%RAML 1.0\n...')
wap.raml08.parse('#%RAML 1.0\n...')

oldParser.parseRAMLSync('#%RAML 1.0\n...')
// Not supported
```

### API Models
```js
const oldParser = require('raml-1-parser')
const wp = require('webapi-parser')
const wap = wp.WebApiParser

const node = await oldParser.loadApi('/path/to/api.raml')
const model = await wap.raml10.parse('file:///path/to/api.raml')
// or
// const model = wap.raml08.parse('file:///path/to/api.raml')

node.resources()
model.encodes.endPoints()
// Note that webapi-parser resources are flat and occur in the order defined in the RAML doc.

node.resources()[0].methods()
model.encodes.endPoints()[0].operations()

node.annotationTypes()
model.declares.filter(el -> el instanceof wp.model.domain.CustomDomainProperty)

node.resourceTypes()
model.declares.filter(el -> el instanceof wp.model.domain.ParametrizedResourceType)

node.schemas()
model.declares.filter(el -> el instanceof wp.model.domain.SchemaShape)

node.securitySchemes()
model.declares.filter(el -> el instanceof wp.model.domain.SecurityScheme)

node.traits()
model.declares.filter(el -> el instanceof wp.model.domain.ParametrizedTrait)

node.types()
model.declares.filter(el -> el instanceof wp.model.domain.AnyShape)

node.expand()
wap.raml10.resolve(model)
```

For more details on navigating the new model, please refer to [Navigating a "WebApi" Model](navigating.md) tutorial.


## Need Assistance?
Here are the things to do if you have more questions:
* Check out more of our [tutorials](SUMMARY.md)
* Explore relevant [examples](https://github.com/raml-org/webapi-parser/tree/master/examples/js)
* Research the API with the [developer documentation](https://raml-org.github.io/webapi-parser/js/modules/webapiparser.html)
* Ask your question at [github issues](https://github.com/raml-org/webapi-parser/issues)
