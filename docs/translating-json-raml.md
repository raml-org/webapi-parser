# Translating JSON Schemas to RAML DataTypes
Using `webapi-parser` and [WebApi Model](https://raml-org.github.io/webapi-parser/js/classes/_webapi_parser_.webapibaseunit.html), it is possible to translate JSON Schemas to RAML Datatypes.

You can take a look at the [complete examples](#complete-examples) for more advanced use-cases.

## Quick start
To translate a JSON Schema to a RAML DataType, you can parse a JSON containing one or more JSON Schemas, then select the data type/schema using the [WebApi Model](https://raml-org.github.io/webapi-parser/js/classes/_webapi_parser_.webapibaseunit.html) and call its `.toRamlDatatype()` method (or property in JavaScript). The output of the method is a RAML 1.0 Library string containing the translated type. Note that the JSON Schema must be wrapped in an OAS 2.0 document.

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

  // Type can be selected using the utility function `getDeclarationByName()`
  console.log(
    'RAML Data Type from definitions using util:\n',
    model.getDeclarationByName('User').toRamlDatatype)
}

main()
```

```java
// java
package co.acme.translate;

import webapi.Oas20;
import webapi.WebApiDocument;
import amf.client.model.document.*;
import amf.client.model.domain.*;

import java.util.concurrent.ExecutionException;

public class JsonSchemaToRamlDt {
  public static void translateFromApi() throws InterruptedException, ExecutionException {
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

    // Type can be selected using the utility function `getDeclarationByName()`
    NodeShape user1 = (NodeShape) doc.getDeclarationByName("User");
    System.out.println(
      "RAML Data Type from definitions using util:\n" +
      user1.toRamlDatatype());
  }
}
```

## Complete examples
* [JavaScript example](https://github.com/raml-org/webapi-parser/blob/master/examples/js/jsonschema-ramldt.js)
* [Java example](https://github.com/raml-org/webapi-parser/blob/master/examples/java/src/main/java/co/acme/translate/JsonSchemaToRamlDt.java)
