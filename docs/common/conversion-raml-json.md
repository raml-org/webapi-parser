# RAML Data Type -> JSON Schema conversion
Using `webapi-parser` conversion tools it is possible to convert RAML 1.0 Data Type from RAML 1.0 API, RAML 1.0 Library or RAML 1.0 DataType Fragment to corresponding JSON Schema.

Please refer to [complete examples](#complete-examples) for more advanced use cases.

## Quick start

To convert RAML Data Type to JSON Schema simply use `Conversion.toJsonSchema` method as described in code below.

Provide file path or content as the first argument and name of the type to be converted as the second argument. Second argument is optional when converting single type from RAML 1.0 DataType Fragment.

```js
// js
const conversion = require('webapi-parser').Conversion

const ramlApi = `
  #%RAML 1.0
  title: API with Types
  types:
    Book:
      type: object
      properties:
        title: string
        author: string
    User:
      type: object
      properties:
        username: string
`

async function main () {
  const user = await conversion.toJsonSchema(ramlApi, 'User')
  console.log('User from API:', user)
}

main()
```

```java
// java
package co.acme.convert;

import webapi.Conversion;

import java.util.concurrent.ExecutionException;

public class RamlToJsonSchema {
  public static void convertFromApi() throws InterruptedException, ExecutionException {
    String inp = "file://../api-specs/raml/api-with-types.raml";
    String converted = Conversion.toJsonSchema(inp, "User").get();
    System.out.println("Converted to JSON Schema:\n" + converted);
  }
}
```

## Complete examples
* [JavaScript example](https://github.com/raml-org/webapi-parser/blob/master/examples/js/convert-ramldt-jsonschema.js)
* [Java example](https://github.com/raml-org/webapi-parser/blob/master/examples/java/src/main/java/co/acme/convert/RamlDtToJsonSchema.java)
