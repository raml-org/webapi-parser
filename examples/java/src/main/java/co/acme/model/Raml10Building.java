package co.acme.model;

import webapi.WebApiParser;
import webapi.Raml10;
import webapi.WebApiDocument;
import amf.client.model.domain.*;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.Arrays;

public class Raml10Building {

  // Example of building RAML 1.0 API from scratch
  public static void buildApi() throws InterruptedException, ExecutionException {
    // Init WebApiParser to set up necessary plugins
    WebApiParser.init().get();

    // Create API instance with title, description, protocols, mediaType,
    // 'accepts' list and version
    final WebApi api = new WebApi()
      .withName("Foo org API")
      .withDescription("Describes Foo org API")
      .withSchemes(Arrays.asList("http", "https"))
      .withContentType(Collections.singletonList("application/json"))
      .withAccepts(Collections.singletonList("application/json"))
      .withVersion("1.1");

    // Set baseUrl
    api.withServer("https://api.spotify.com/v/{version}");

    // Create endpoint with path /users, title and description
    final EndPoint users = api.withEndPoint("/users");
    users
      .withName("Users endpoint")
      .withDescription("Lists users");

    // Create Request instance with query parameter "ids" of type string
    final Request usersRequest = new Request();
    usersRequest
      .withQueryParameter("ids")
      .withRequired(true)
      .withName("ids")
      .withDescription("A comma-separated list of IDs")
      .withScalarSchema("displayName").withDataType("http://www.w3.org/2001/XMLSchema#string");

    // Create method GET /users with title
    final Operation getUsers = users.withOperation("get");

    // Set GET /users title, description and request params
    getUsers
      .withName("GET Users")
      .withDescription("Get Several Users")
      .withRequest(usersRequest);

    // Create 500 response to GET /users
    final Response getUsers500Resp = getUsers.withResponse("500");

    // Create GET /users 500 response payload of content type 'text/plain' and
    // data type of 'string'
    final Payload getUsers500Payload = getUsers500Resp.withPayload("text/plain");
    final ScalarShape getUsers500Schema = getUsers500Payload.withScalarSchema("500ErrorMessage");
    getUsers500Schema.withDataType("http://www.w3.org/2001/XMLSchema#string");

    // Create 200 response to GET /users
    final Response getUsers200Resp = getUsers.withResponse("200");

    // Create GET /users 200 response payload of content type 'application/json'
    // with schema
    final Payload getUsers200Payload = getUsers200Resp.withPayload("application/json");
    getUsers200Payload.withMediaType("application/json");
    final NodeShape getUsers200Schema = getUsers200Payload.withObjectSchema("schema");
    getUsers200Schema.withClosed(false);

    // Create required 'username' schema property of type 'string'
    final ScalarShape userNameSchema = new ScalarShape();
    userNameSchema.withDataType("http://www.w3.org/2001/XMLSchema#string");
    final PropertyShape userName = getUsers200Schema.withProperty("username");
    userName
      .withMinCount(1)
      .withPath("http://a.ml/vocabularies/data#name")
      .withRange(userNameSchema);

    // Create required 'email' schema property of type 'string'
    final ScalarShape userEmailSchema = new ScalarShape();
    userEmailSchema.withDataType("http://www.w3.org/2001/XMLSchema#string");
    final PropertyShape userEmail = getUsers200Schema.withProperty("email");
    userEmail
      .withMinCount(1)
      .withPath("http://a.ml/vocabularies/data#email")
      .withRange(userEmailSchema);

    // Create endpoint /users/{id}
    final EndPoint user = api.withEndPoint("/users/{id}");
    user
      .withName("User endpoint")
      .withDescription("Get user");

    // Create document with the constructed API
    WebApiDocument model = new WebApiDocument();
    model.withEncodes(api);

    // Generate RAML 1.0 string from the document
    final String generated = Raml10.generateString(model).get();
    System.out.println("Generated from constructed: " + generated);
  }
}
