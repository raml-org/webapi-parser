---
---

# Translating RAML DataTypes to JSON Schemas
Using `webapi-parser` and [WebApi Model](https://raml-org.github.io/webapi-parser/js/classes/_webapi_parser_.webapibaseunit.html), it is possible to translate RAML 1.0 DataTypes contained inside RAML 1.0, RAML 1.0 Library and RAML 1.0 DataType documents to JSON Schemas.

You can take a look at the [complete examples](#complete-examples) for more advanced use cases.

## Quick start
To translate a RAML DataType to a JSON Schema, you can parse a RAML document, then select the data type using [WebApi Model](https://raml-org.github.io/webapi-parser/js/classes/_webapi_parser_.webapibaseunit.html) and call its `.toJsonSchema()` method (or property in JavaScript). The output of the method is a JSON Schema string representating the original RAML DataType.

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
  // Type can be selected using the utility function `getDeclarationByName()`
  console.log(libModel.getDeclarationByName('Book').toJsonSchema)

  // Type can also be selected by index
  console.log(libModel.declares[0].toJsonSchema)
}

main()
```

```java
// java
package co.acme.translate;

import webapi.Raml10;
import webapi.WebApiModule;
import amf.client.model.domain.*;

import java.util.concurrent.ExecutionException;

public class RamlToJsonSchema {
  public static void translateFromLibrary() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0 Library\n" +
                "types:\n" +
                "  Book:\n" +
                "    type: object\n" +
                "    properties:\n" +
                "      title: string\n" +
                "      author: string\n";
    WebApiModule doc = (WebApiModule) Raml10.parse(inp).get();
    // Type can be selected using the utility function `getDeclarationByName()`
    AnyShape book = (AnyShape) doc.getDeclarationByName("Book").get();
    System.out.println(book.toJsonSchema());

    // Type can also be selected by index
    NodeShape book2 = (NodeShape) doc.declares().get(0);
    System.out.println(book2.toJsonSchema());
  }
}
```

## Complete examples
* [JavaScript example](https://github.com/raml-org/webapi-parser/blob/master/examples/js/ramldt-jsonschema.js)
* [Java example](https://github.com/raml-org/webapi-parser/blob/master/examples/java/src/main/java/co/acme/translate/RamlDtToJsonSchema.java)
