# RAML Data Type -> JSON Schema conversion
Using `webapi-parser` and [AMF Model](https://raml-org.github.io/webapi-parser/js/classes/_amf_client_js_.model.document.baseunit.html) it is possible to convert JSON Schema from OAS 2.0 (or another) document to RAML Data Type included in RAML 1.0 Library.

Please refer to [complete examples](#complete-examples) for more advanced use cases.

## Quick start

We are going to use [OAS 2.0 Pet Store API](https://github.com/raml-org/webapi-parser/blob/master/examples/api-specs/oas/pet-store-api.json) in following examples.

To convert JSON Schema to RAML Data Type, parse the document of your choice, reach data type using AMF Model and call its `.toRamlDatatype()` method (or property in JS case). Output of the methods is RAML 1.0 Library string containing converted type.

```js
// js
const wap = require('webapi-parser').WebApiParser
const path = require('path')


async function main () {
  const inPath = path.join(__dirname, '../api-specs/oas/pet-store-api.json')
  const docPath = `file://${inPath}`
  const model = await wap.oas20.parse(docPath)

  // Convert type from "definitions".
  // Type can be picked using utility functions.
  console.log(
    'RAML Data Type from definitions using util:\n',
    model.findById(`${docPath}#/declarations/types/Pet`).toRamlDatatype)

  // Type can also be picked by index.
  console.log(
    'RAML Data Type from definitions by index:\n',
    model.declares[0].toRamlDatatype)
}

main()
```

```java
// java
package co.acme.convert;

import webapi.Oas20;
import amf.client.model.document.*;
import amf.client.model.domain.*;

import java.util.concurrent.ExecutionException;

public class JsonSchemaToRamlDt {
  public static void convertFromApi() throws InterruptedException, ExecutionException {
    String docPath = "file://../api-specs/oas/pet-store-api.json";
    Document doc = (Document) Oas20.parse(docPath).get();

    // Convert type from root. Type can be picked using utility functions
    NodeShape pet = (NodeShape) doc.findById(
      docPath + "#/declarations/types/Pet").get();
    System.out.println(
      "RAML Data Type from definitions using util:\n" +
      pet.toRamlDatatype());

    // Type can also be picked by index.
    NodeShape pet2 = (NodeShape) doc.declares().get(0);
    System.out.println(
      "RAML Data Type from definitions by index:\n" +
      pet2.toRamlDatatype());
  }
}
```

## Complete examples
* [JavaScript example](https://github.com/raml-org/webapi-parser/blob/master/examples/js/convert-jsonschema-ramldt.js)
* [Java example](https://github.com/raml-org/webapi-parser/blob/master/examples/java/src/main/java/co/acme/convert/JsonSchemaToRamlDt.java)
