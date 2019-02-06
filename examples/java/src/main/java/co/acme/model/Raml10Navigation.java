package co.acme.model;

import webapi.Raml10;
import amf.client.model.document.Document;
import amf.client.model.domain.*;

import java.util.concurrent.ExecutionException;

public class Raml10Navigation {

  // Example of navigating RAML 1.0 API document.
  public static void navigateApi() throws InterruptedException, ExecutionException {
    // Parse RAML 1.0 file
    String inp = "file://../api-specs/raml/navigation-example-api.raml";
    Document model = (Document) Raml10.parse(inp).get();

    // Access RAML 1.0 API
    WebApi api = (WebApi) model.encodes();

    // API root properties
    System.out.println("Title: " + api.name().value());
    System.out.println("Description: " + api.description().value());
    System.out.println("First doc title: " + api.documentations().get(0).title().value());
    System.out.println("First doc content: " + api.documentations().get(0).description().value());
    System.out.println("Version: " + api.version().value());
    System.out.println("First protocol: " + api.schemes().get(0).value());
    System.out.println("Base uri: " + api.servers().get(0).url().value());

    // Access security scheme from root
    System.out.println(
      "First security scheme name: " +
      api.security().get(0).scheme().name().value());
    System.out.println(
      "First security scheme description: " +
      api.security().get(0).scheme().description().value());

    // Endpoint /users
    EndPoint users = (EndPoint) api.endPoints().get(0);
    System.out.println("Path: " + users.path().value());
    System.out.println("Relative path: " + users.relativePath());
    ParametrizedResourceType appliedRt = (ParametrizedResourceType) users.extendsNode().get(0);
    System.out.println("Resource type: " + appliedRt.name().value());

    // Access resource type properties from endpoint
    ObjectNode referencedRf = (ObjectNode) ((ResourceType) appliedRt.target().linkTarget().get()).dataNode();
    ObjectNode csrfGet = (ObjectNode) referencedRf.properties().get("post");
    ObjectNode csrfQParams = (ObjectNode) csrfGet.properties().get("queryParameters");
    ObjectNode csrfItself = (ObjectNode) csrfQParams.properties().get("csrf");
    ScalarNode csrfTypeProp = (ScalarNode) csrfItself.properties().get("type");
    System.out.println("Page query param `csrf` type: " + csrfTypeProp.value());

    // POST /users
    Operation postUsers = (Operation) users.operations().get(0);

    // Access trait properties from endpoint
    ParametrizedTrait appliedTrait = (ParametrizedTrait) postUsers.extendsNode().get(0);
    System.out.println("Trait: " + appliedTrait.name().value());
    ObjectNode refTrait = (ObjectNode) ((Trait) appliedTrait.target().linkTarget().get()).dataNode();
    ObjectNode trHeaders = (ObjectNode) refTrait.properties().get("headers");
    ObjectNode trXTracker = (ObjectNode) trHeaders.properties().get("X-Tracker");
    ScalarNode trHeaderDesc = (ScalarNode) trXTracker.properties().get("description");
    System.out.println("Trait header desc: " + trHeaderDesc.value());

    // POST /users request payload
    Payload postUsersReq = (Payload) ((Request) postUsers.request()).payloads().get(0);
    System.out.println("Request media type: " + postUsersReq.mediaType().value());

    // Access User type from request
    NodeShape postUsersType = (NodeShape) postUsersReq.schema().inherits().get(0).linkTarget().get();
    System.out.println("Type name: " + postUsersType.name().value());
    System.out.println("Second property name: " + postUsersType.properties().get(1).name().value());
    System.out.println(
      "Second property type: " +
      ((ScalarShape) postUsersType.properties().get(1).range()).dataType());

    // Endpoint /users/{id}
    EndPoint user = (EndPoint) api.endPoints().get(1);
    System.out.println("Path: " + user.path().value());
    System.out.println("Relative path: " + user.relativePath());
    System.out.println(
      "First annotation name: " +
      user.customDomainProperties().get(0).name().value());
    System.out.println(
      "First annotation type: " +
      ((ScalarNode) user.customDomainProperties().get(0).extension()).value());

    // GET /users/{id}
    Operation getUser = (Operation) user.operations().get(0);
    System.out.println("Status code: " + getUser.responses().get(0).statusCode().value());
    Payload getUserResp = (Payload) getUser.responses().get(0).payloads().get(0);
    System.out.println("Response media type: " + getUserResp.mediaType().value());

    // Access User type from response
    NodeShape getUsersType = (NodeShape) getUserResp.schema().inherits().get(0).linkTarget().get();
    System.out.println("Type name: " + getUsersType.name().value());
    System.out.println("First property name: " + getUsersType.properties().get(0).name().value());
    System.out.println(
      "First property type: " +
      ((ScalarShape) postUsersType.properties().get(0).range()).dataType());

    // Annotation 'experimental'
    CustomDomainProperty annotation = (CustomDomainProperty) model.declares().get(0);
    System.out.println("Annotation name: " + annotation.name().value());
    System.out.println("Annotation type: " + ((ScalarShape) annotation.schema()).dataType());

    // Type 'User'
    NodeShape userType = (NodeShape) model.declares().get(1);
    System.out.println("User type properties:");
    for (int i = 0; i < userType.properties().size(); i++) {
      System.out.println(
        userType.properties().get(i).name().value() + ": " +
        ((ScalarShape) userType.properties().get(i).range()).dataType()
      );
    }
    ScalarShape age = (ScalarShape) userType.properties().get(2).range();
    System.out.println("Age from " + age.minimum().value() + " to " + age.maximum().value());

    // ResourceType 'postable'
    ResourceType postable = (ResourceType) model.declares().get(2);
    ObjectNode postableNode = (ObjectNode) postable.dataNode();
    ObjectNode postableGet = (ObjectNode) postableNode.properties().get("post");
    ObjectNode pgQparams = (ObjectNode) postableGet.properties().get("queryParameters");
    ObjectNode postableCsrtItself = (ObjectNode) pgQparams.properties().get("csrf");
    ScalarNode postableCsrfTypeProp = (ScalarNode) postableCsrtItself.properties().get("type");
    System.out.println("Page query param `csrf` type: " + postableCsrfTypeProp.value());

    // Trait 'traceable'
    Trait traceable = (Trait) model.declares().get(3);
    ObjectNode traceableNode = (ObjectNode) traceable.dataNode();
    ObjectNode traceHeaders = (ObjectNode) traceableNode.properties().get("headers");
    ObjectNode traceXTracker = (ObjectNode) traceHeaders.properties().get("X-Tracker");
    ScalarNode traceHeaderDesc = (ScalarNode) traceXTracker.properties().get("description");
    System.out.println("Traceable header desc: " + traceHeaderDesc.value());
    ScalarNode traceHeaderPattern = (ScalarNode) traceXTracker.properties().get("pattern");
    System.out.println("Traceable header pattern: " + traceHeaderPattern.value());
    ScalarNode traceHeaderExample = (ScalarNode) traceXTracker.properties().get("example");
    System.out.println("Traceable header example: " + traceHeaderExample.value());

    // SecurityScheme oauth_1_0
    SecurityScheme oauth1 = (SecurityScheme) model.declares().get(4);
    System.out.println("Description: " + oauth1.description().value());
    System.out.println("Type: " + oauth1.type().value());
    OAuth1Settings settings = (OAuth1Settings) oauth1.settings();
    System.out.println("requestTokenUri: " + settings.requestTokenUri().value());
    System.out.println("authorizationUri: " + settings.authorizationUri().value());
    System.out.println("tokenCredentialsUri: " + settings.tokenCredentialsUri().value());
  }
}
