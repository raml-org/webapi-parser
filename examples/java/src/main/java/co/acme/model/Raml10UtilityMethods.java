package co.acme.model;

import webapi.Raml10;
import webapi.WebApiDocument;
import webapi.WebApiDataType;
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

    // Get type defined in root by name
    AnyShape userInRoot = (AnyShape) model.getDeclarationByName("User");
    NodeShape userInRootNode = (NodeShape) userInRoot;
    System.out.println(userInRootNode.toJsonSchema());
    ScalarShape age = (ScalarShape) userInRootNode.properties().get(2).range();
    System.out.println("Age from " + age.minimum().value() + " to " + age.maximum().value());

    // Navigating RAML 1.0 DataType string
    String ramlStr ="#%RAML 1.0 DataType\n" +
                    "\n" +
                    "type: object\n" +
                    "properties:\n" +
                    "  firstName: string\n" +
                    "  lastName:  string";
    WebApiDataType stringModel = (WebApiDataType) Raml10.parse(ramlStr).get();

    // Get type defined in root by name. Types without explicit name have a
    // special name "type"
    AnyShape userInStringRoot = (AnyShape) stringModel.getDeclarationByName("type");
    System.out.println(userInStringRoot.toJsonSchema());
  }
}
