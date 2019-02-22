package co.acme.convert;

import webapi.Oas20;
import amf.client.model.document.*;
import amf.client.model.domain.*;

import java.util.concurrent.ExecutionException;

public class JsonSchemaToRamlDt {

  // Example of converting single JSON Schema from OAS 2.0 document to RAML Data Type
  public static void convertFromApi() throws InterruptedException, ExecutionException {
    String docPath = "file://../api-specs/oas/pet-store-api.json";
    Document doc = (Document) Oas20.parse(docPath).get();

    // Convert type from root. Type can be picked using utility functions
    NodeShape pet = (NodeShape) doc.findById(
      docPath + "#/declarations/types/Pet").get();
    System.out.println(
      "RAML Data Type from definitions using util:\n" +
      pet.toJsonSchema());

    // Type can also be picked by index.
    NodeShape pet2 = (NodeShape) doc.declares().get(0);
    System.out.println(
      "JSON from API root by index:\n" +
      pet2.toJsonSchema());

    // To properly convert type with references, model needs to be
    // resolved first.
    Document resolved = (Document) Oas20.resolve(doc).get();
    WebApi api = (WebApi) resolved.encodes();
    EndPoint ownerEndpoint = (EndPoint) api.endPoints().get(1);
    Operation getOwner = (Operation) ownerEndpoint.operations().get(0);
    Payload getOwnerPayload = (Payload) getOwner.responses().get(0).payloads().get(0);
    NodeShape owner = (NodeShape) getOwnerPayload.schema();
    System.out.println(
      "RAML Data Type (with references) from resolved document:\n" +
      owner.toJsonSchema());
  }
}
