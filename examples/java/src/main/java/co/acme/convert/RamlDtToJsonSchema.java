package co.acme.convert;

import webapi.Raml10;
import amf.client.model.document.*;
import amf.client.model.domain.*;

import java.util.concurrent.ExecutionException;

public class RamlDtToJsonSchema {

  // Example of converting single RAML Data Type from RAML 1.0 API Document string
  public static void convertFromApi() throws InterruptedException, ExecutionException {
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
    Document doc = (Document) Raml10.parse(inp).get();

    // Convert type from root. Type can be picked using utility functions
    NodeShape user = (NodeShape) doc.findById(
      "http://a.ml/amf/default_document#/declarations/types/User").get();
    System.out.println("JSON from API root using util:\n" + user.toJsonSchema());

    // Type can also be picked by index.
    NodeShape user2 = (NodeShape) doc.declares().get(0);
    System.out.println("JSON from API root by index:\n" + user2.toJsonSchema());

    // To convert type referenced in response payload, model needs to be
    // resolved first.
    // Note we are picking first payload of first response of first
    // method etc here.
    Document resolved = (Document) Raml10.resolve(doc).get();
    WebApi api = (WebApi) resolved.encodes();
    EndPoint users = (EndPoint) api.endPoints().get(0);
    Operation getUsers = (Operation) users.operations().get(0);
    Payload getUserPayload = (Payload) getUsers.responses().get(0).payloads().get(0);
    NodeShape user3 = (NodeShape) getUserPayload.schema();
    System.out.println("JSON from resolved API:\n" + user3.toJsonSchema());
  }

  // Example of converting single RAML Data Type from RAML 1.0 Library string
  public static void convertFromLibrary() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0 Library\n" +
                "types:\n" +
                "  Book:\n" +
                "    type: object\n" +
                "    properties:\n" +
                "      title: string\n" +
                "      author: string\n";
    Module doc = (Module) Raml10.parse(inp).get();
    // Convert type from root. Type can be picked using utility functions
    NodeShape book = (NodeShape) doc.findById(
      "http://a.ml/amf/default_document#/declarations/types/Book").get();
    System.out.println("JSON from Library root using util:\n" + book.toJsonSchema());

    // Type can also be picked by index.
    NodeShape book2 = (NodeShape) doc.declares().get(0);
    System.out.println("JSON from Library root by index:\n" + book2.toJsonSchema());
  }

  // Example of converting single RAML Data Type from RAML 1.0 DataType Fragment string
  public static void convertFromDataType() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0 DataType\n" +
                "type: object\n" +
                "properties:\n" +
                "  title: string\n" +
                "  author: string\n";
    DataType doc = (DataType) Raml10.parse(inp).get();
    NodeShape book = (NodeShape) doc.encodes();
    System.out.println("JSON from DataType:\n" + book.toJsonSchema());
  }
}
