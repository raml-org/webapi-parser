# RAML Data Type -> JSON Schema conversion
Using `webapi-parser` and [WebApi Model](https://raml-org.github.io/webapi-parser/js/classes/_webapi_parser_.webapibaseunit.html) it is possible to convert RAML 1.0 Data Type from RAML 1.0 API, RAML 1.0 Library or RAML 1.0 DataType Fragment to corresponding JSON Schema.

Please refer to [complete examples](#complete-examples) for more advanced use cases.

## Quick start

To convert RAML Data Type to JSON Schema, parse RAML document, reach data type using WebApi Model and call its `.toJsonSchema()` method. Output of the methods is JSON Schema string of converted type.

```js
// js
const wap = require('webapi-parser').WebApiParser

const ramlLibrary = `
  #%RAML 1.0 Library
  types:
    Book:
      type: object
      properties:
        title: string
        author: string
`

async function main () {
  const libModel = await wap.raml10.parse(ramlLibrary)
  // Convert type from root. Type can be picked using utility functions
  console.log(libModel.getDeclarationByName('Book').toJsonSchema)
  // Type can also be picked by index.
  console.log(libModel.declares[0].toJsonSchema)
}

main()
```

```java
// java
package co.acme.convert;

import webapi.Raml10;
import amf.client.model.document.*;
import amf.client.model.domain.*;

import java.util.concurrent.ExecutionException;

public class RamlToJsonSchema {
  public static void convertFromApi() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0 Library\n" +
                "types:\n" +
                "  Book:\n" +
                "    type: object\n" +
                "    properties:\n" +
                "      title: string\n" +
                "      author: string\n";
    Module doc = (Module) Raml10.parse(inp).get();
    // Convert type from root. Type can be picked using utility functions
    NodeShape book = (NodeShape) doc.getDeclarationByName("Book").get();
    System.out.println(book.toJsonSchema());

    // Type can also be picked by index.
    NodeShape book2 = (NodeShape) doc.declares().get(0);
    System.out.println(book2.toJsonSchema());
  }
}
```

## Complete examples
* [JavaScript example](https://github.com/raml-org/webapi-parser/blob/master/examples/js/convert-ramldt-jsonschema.js)
* [Java example](https://github.com/raml-org/webapi-parser/blob/master/examples/java/src/main/java/co/acme/convert/RamlDtToJsonSchema.java)
