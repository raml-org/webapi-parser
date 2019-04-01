package co.acme.model;

import webapi.Raml10;
import webapi.WebApiDocument;
import amf.client.model.domain.*;

import java.util.concurrent.ExecutionException;
import java.net.URLEncoder;
import java.util.List;
import java.io.UnsupportedEncodingException;


public class Raml10UtilityMethods {

  // Example of using utility methods to get data from RAML 1.0 doc
  public static void navigateApi() throws InterruptedException, ExecutionException, UnsupportedEncodingException {
    // Navigating RAML 1.0 file
    String docPath = "file://../api-specs/raml/navigation-example-api.raml";
    WebApiDocument model = (WebApiDocument) Raml10.parse(docPath).get();

    // Access RAML 1.0 API
    WebApi api = (WebApi) model.encodes();

    // Get all types (both defined in root and in endpoints)
    List<DomainElement> allTypes = model.findByType("http://www.w3.org/ns/shacl#NodeShape");

    // Get type defined in root
    // An ID to get type from root will look like:
    // file:///somewhere/api-specs/raml/api-with-types.raml#/declarations/types/User
    NodeShape userInRoot = (NodeShape) model.findById(
      docPath + "#/declarations/types/User").get();
    ScalarShape age = (ScalarShape) userInRoot.properties().get(2).range();
    System.out.println("Age from " + age.minimum().value() + " to " + age.maximum().value());

    // Get type reference in /users/{id} response schema.
    // Note that values from RAML must be url-encoded to be used by
    // utility functions
    String endpoint = URLEncoder.encode("/users/{id}", "UTF-8");
    String ct = URLEncoder.encode("application/json", "UTF-8");
    NodeShape userRefFromEndpoint = (NodeShape)  model.findById(
      docPath +  "#/web-api/end-points/" + endpoint +
      "/get/200/" + ct + "/schema").get();

    // Get type which is referenced by `userRefFromEndpoint`
    NodeShape userInEndpoint = (NodeShape) userRefFromEndpoint
      .inherits().get(0).linkTarget().get();
    System.out.println(userInEndpoint.id() == userInRoot.id());


    // Navigating RAML 1.0 string
    String ramlStr ="#%RAML 1.0\n" +
                    "\n" +
                    "title: ACME Banking HTTP API\n" +
                    "types:\n" +
                    "  User:\n" +
                    "    type: object";
    WebApiDocument stringModel = (WebApiDocument) Raml10.parse(ramlStr).get();

    // The only difference from "working with files" is that ID strings
    // should include default AML document path instead of a file system
    // path. An ID to get type from root would look like:
    // http://a.ml/amf/default_document#/declarations/types/User
    String strDocPath = "http://a.ml/amf/default_document";
    NodeShape userInStringRoot = (NodeShape) stringModel.findById(
      strDocPath + "#/declarations/types/User").get();
  }
}
