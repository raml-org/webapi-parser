---
---

# Migration guide (datatype-expansion)
Welcome!

As you may already know RAML JS tool `datatype-expansion` has been deprecated. This guide describes a similar functionality offered by `webapi-parser`, thus allowing to migrate from `datatype-expansion` to `webapi-parser`.

## datatype-expansion and webapi-parser
While `datatype-expansion` is a utility tool to expand a given type and create a canonical form, `webapi-parser` is an API Spec parser which currently supports RAML 0.8, RAML 1.0, OAS 2.0 and OAS 3.0(beta).

Among other `webapi-parser` functionality is an API/process called "resolution". This process is performed by syntax-specific `.resolve()` methods of all supported API syntaxes (RAML, OAS, AMF Graph).

Resolving produces a "flat" document/model with all the references replaced by redundant copies of an actual data, thus replacing functionality offered by `datatype-expansion`. `webapi-parser` can resolve RAML APIs and all the supported fragments (Library, DataType, etc.).

For more details or resolution process, please see the [Resolving a "WebApi" Model](https://raml-org.github.io/webapi-parser/resolving.html) document.

## Installing webapi-parser
To install `webapi-parser` follow guidelines from the respective [readme section](https://github.com/raml-org/webapi-parser#javascript).

## Example project
### Files

`input.raml`
```yaml
#%RAML 1.0 Library
types:
  User:
    type: object
    properties:
      firstName: string
      lastName:  string
  Admin:
    type: User
    properties:
      permissions:
        type: array
        items: string
```

`index.js`
```js
const wap = require('webapi-parser').WebApiParser
const path = require('path')

async function main () {
  // Step 1
  const inputPath = path.join(__dirname, 'input.raml')
  const model = await wap.raml10.parse(`file://${inputPath}`)

  // Step 2
  const resolved = await wap.raml10.resolve(model)

  // Step 3
  const outPath = path.join(__dirname, './output.raml')
  await wap.raml10.generateFile(resolved, `file://${outPath}`)
}

main()
```

`output.raml`
```yaml
#%RAML 1.0 Library
types:
  User:
    type: object
    additionalProperties: true
    properties:
      firstName:
        type: string
        required: true
      lastName:
        type: string
        required: true
  Admin:
    type: object
    additionalProperties: true
    properties:
      firstName:
        type: string
        required: true
      lastName:
        type: string
        required: true
      permissions:
        type: array
        items:
          type: string
        required: true
```

### Explanation
Now considering the input (`input.raml`) let's describe contents of `index.js` line-by-line.

First all the necessary modules are imported. This includes `webapi-parser` namespace `WebApiParser` which contains most of the lib's public API:
```js
const wap = require('webapi-parser').WebApiParser
const path = require('path')
```

Then the input file `input.raml` is parsed by calling the `.parse()` method from the `raml10` namespace. Input of the method can be either file path or a RAML string. Parsing produces a ["WebApi" Model](https://raml-org.github.io/webapi-parser/js/classes/webapibaseunit.html):
```js
  const inputPath = path.join(__dirname, 'input.raml')
  const model = await wap.raml10.parse(`file://${inputPath}`)
```

Next line is where the magic happens. By calling the `.resolve()` method parsed model is "resolved", producing a "resolved"/expanded model:
```js
  const resolved = await wap.raml10.resolve(model)
```

To conclude our processing we generate output RAML file (`output.raml`) from the resolved model:
```js
  const outPath = path.join(__dirname, './output.raml')
  await wap.raml10.generateFile(resolved, `file://${outPath}`)
```

We could also generate a RAML string instead of the file by calling:
```js
  const ramlString = wap.raml10.generateString(resolved)
```

Feel free to inspect contents of the `output.raml` file above and note how types definitions and their inheritance was flattened/expanded.

Note that "WebApi" Model functionality isn't limited to output generation. It can also be inspected and altered in JS and Java code and converter to other API spec formats (OAS2.0, OAS 3.0, etc.).

## Need Assistance?
Here are the things to do if you have more questions:
* Check out more of our [tutorials](SUMMARY.md)
* Explore relevant [examples](https://github.com/raml-org/webapi-parser/tree/master/examples/js)
* Research the API with the [developer documentation](https://raml-org.github.io/webapi-parser/js/modules/webapiparser.html)
* Ask your question at [github issues](https://github.com/raml-org/webapi-parser/issues)
