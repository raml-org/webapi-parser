package co.acme.convert;

import webapi.Oas20;
import webapi.WebApiDocument;
import amf.client.model.document.*;
import amf.client.model.domain.*;

import java.util.concurrent.ExecutionException;

public class JsonSchemaToRamlDt {

  // Example of converting single JSON Schema to RAML Data Type
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

    // Type can also be picked by index.
    NodeShape user2 = (NodeShape) doc.declares().get(0);
    System.out.println(
      "RAML Data Type from definitions by index:\n" +
      user2.toRamlDatatype());
  }
}
