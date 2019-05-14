package co.acme.translate;

import webapi.Raml10;
import webapi.WebApiDocument;
import webapi.WebApiModule;
import webapi.WebApiDataType;
import amf.client.model.domain.*;

import java.util.concurrent.ExecutionException;

public class RamlDtToJsonSchema {

  // Example of translating a single RAML DataType from a RAML 1.0 API Document string
  public static void translateFromApi() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0\n" +
                "title: API with Types\n" +
                "types:\n" +
                "  User:\n" +
                "    type: object\n" +
                "    properties:\n" +
                "      firstName: string\n" +
                "      lastName:  string\n" +
                "      age:\n" +
                "        type: integer\n" +
                "        minimum: 0\n" +
                "        maximum: 99\n" +
                "/users/{id}:\n" +
                "  get:\n" +
                "    responses:\n" +
                "      200:\n" +
                "        body:\n" +
                "          application/json:\n" +
                "            type: User";
    WebApiDocument doc = (WebApiDocument) Raml10.parse(inp).get();

    // Type can be accessed using the utility function `getDeclarationByName()`
    NodeShape user = (NodeShape) doc.getDeclarationByName("User");
    System.out.println("JSON from API root using util:\n" + user.toJsonSchema());

    // Type can also be accessed by index.
    NodeShape user2 = (NodeShape) doc.declares().get(0);
    System.out.println("JSON from API root by index:\n" + user2.toJsonSchema());

    // To translate types referenced in a response payload, the model needs to be resolved first
    WebApiDocument resolved = (WebApiDocument) Raml10.resolve(doc).get();
    // Note that the .get(0) below correspond to the 1st payload of the 1st response of the 1st method, etc..
    WebApi api = (WebApi) resolved.encodes();
    EndPoint users = (EndPoint) api.endPoints().get(0);
    Operation getUsers = (Operation) users.operations().get(0);
    Payload getUserPayload = (Payload) getUsers.responses().get(0).payloads().get(0);
    NodeShape user3 = (NodeShape) getUserPayload.schema();
    System.out.println("JSON from resolved API:\n" + user3.toJsonSchema());
  }

  // Example of translating a RAML DataType contained im a RAML 1.0 Library string
  public static void translateFromLibrary() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0 Library\n" +
                "types:\n" +
                "  Book:\n" +
                "    type: object\n" +
                "    properties:\n" +
                "      title: string\n" +
                "      author: string\n";
    WebApiModule doc = (WebApiModule) Raml10.parse(inp).get();
    // Type can be accessed using utility function `getDeclarationByName()`
    NodeShape book = (NodeShape) doc.getDeclarationByName("Book");
    System.out.println("JSON from Library root using util:\n" + book.toJsonSchema());

    // Type can also be accessed by index
    NodeShape book2 = (NodeShape) doc.declares().get(0);
    System.out.println("JSON from Library root by index:\n" + book2.toJsonSchema());
  }

  // Example of translating a RAML DataType contained in a RAML 1.0 DataType string
  public static void translateFromDataType() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0 DataType\n" +
                "type: object\n" +
                "properties:\n" +
                "  title: string\n" +
                "  author: string\n";
    WebApiDataType doc = (WebApiDataType) Raml10.parse(inp).get();
    NodeShape book = (NodeShape) doc.encodes();
    System.out.println("JSON from DataType:\n" + book.toJsonSchema());
  }
}
