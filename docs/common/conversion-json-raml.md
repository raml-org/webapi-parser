# JSON Schema -> RAML Data Type conversion
Using `webapi-parser` and [WebApi Model](https://raml-org.github.io/webapi-parser/js/classes/_webapi_parser_.webapibaseunit.html), it is possible to convert JSON Schemas to RAML Datatypes.

Please refer to [complete examples](#complete-examples) for more advanced use cases.

## Quick start

To convert JSON Schema to RAML Data Type, parse the document of your choice, reach data type using WebApi Model and call its `.toRamlDatatype()` method (or property in JS case). Output of the method is RAML 1.0 Library string containing converted type.

To convert plain JSON Schema string to RAML DataType it has to be wrapped in a OAS 2.0 document.

```js
// js
const wap = require('webapi-parser').WebApiParser
const path = require('path')

const schema = `
  {
    "$schema": "http://json-schema.org/draft-04/schema",
    "type": "object",
    "required": ["firstName", "lastName", "age"],
    "properties": {
      "firstName": {"type": "string"},
      "lastName": {"type": "string"},
      "age": {"type": "integer", "minimum": 0, "maximum": 99}
    }
  }
`

async function main () {
  const parsedSchema = {
    openapi: '2.0',
    definitions: {
      User: JSON.parse(schema)
    }
  }

  // Parse an API document string
  const model = await wap.oas20.parse(JSON.stringify(parsedSchema))

  // Convert type from "definitions".
  // Type can be picked using utility functions.
  console.log(
    'RAML Data Type from definitions using util:\n',
    model.getDeclarationByName('User').toRamlDatatype)
}

main()
```

```java
// java
package co.acme.convert;

import webapi.Oas20;
import webapi.WebApiDocument;
import amf.client.model.document.*;
import amf.client.model.domain.*;

import java.util.concurrent.ExecutionException;

public class JsonSchemaToRamlDt {
  public static void convertFromApi() throws InterruptedException, ExecutionException {
    String jsonSchema = "{\n" +
                  "\"$schema\": \"http://json-schema.org/draft-04/schema\",\n" +
                  "\"type\": \"object\",\n" +
                  "\"required\": [\"firstName\", \"lastName\", \"age\"],\n" +
                  "\"properties\": {\n" +
                    "\"firstName\": {\"type\": \"string\"},\n" +
                    "\"lastName\": {\"type\": \"string\"},\n" +
                    "\"age\": {\"type\": \"integer\", \"minimum\": 0, \"maximum\": 99}\n" +
                  "}\n" +
                "}\n";
    String oasDoc = String.format(
      "{\"openapi\": \"2.0\", \"definitions\": {\"User\": %s}",
      jsonSchema);
    WebApiDocument doc = (WebApiDocument) Oas20.parse(oasDoc).get();

    // Convert type from root. Type can be picked using utility functions
    NodeShape user1 = (NodeShape) doc.getDeclarationByName("User");
    System.out.println(
      "RAML Data Type from definitions using util:\n" +
      user1.toRamlDatatype());
  }
}
```

## Complete examples
* [JavaScript example](https://github.com/raml-org/webapi-parser/blob/master/examples/js/convert-jsonschema-ramldt.js)
* [Java example](https://github.com/raml-org/webapi-parser/blob/master/examples/java/src/main/java/co/acme/convert/JsonSchemaToRamlDt.java)
