package co.acme.model;

import webapi.WebApiParser;
import webapi.Raml10;
import amf.client.model.document.Document;
import amf.client.model.domain.*;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.Arrays;

public class Raml10Building {
  public static void buildApi() throws InterruptedException, ExecutionException {
    WebApiParser.init().get();

    final WebApi api = new WebApi()
        .withName("Foo org API")
        .withDescription("Describes Foo org API")
        .withSchemes(Arrays.asList("http", "https"))
        .withContentType(Collections.singletonList("application/json"))
        .withAccepts(Collections.singletonList("application/json"))
        .withVersion("1.1");
    api.withServer("https://api.spotify.com/v/{version}");

    final EndPoint users = api.withEndPoint("/users");
    users
        .withName("Users endpoint")
        .withDescription("Lists users");

    final Request usersRequest = new Request();

    usersRequest
        .withQueryParameter("ids")
        .withRequired(true)
        .withName("ids")
        .withDescription("A comma-separated list of IDs")
        .withScalarSchema("displayName").withDataType("http://www.w3.org/2001/XMLSchema#string");

    final Operation getUsers = users.withOperation("get");
    getUsers
        .withName("GET Users")
        .withDescription("Get Several Users")
        .withRequest(usersRequest);

    final Response getUsers500Resp = getUsers.withResponse("500");
    final Payload getUsers500Payload = getUsers500Resp.withPayload("text/plain");
    final ScalarShape getUsers500Schema = getUsers500Payload.withScalarSchema("500ErrorMessage");
    getUsers500Schema.withDataType("http://www.w3.org/2001/XMLSchema#string");

    final Response getUsers200Resp = getUsers.withResponse("200");
    final Payload getUsers200Payload = getUsers200Resp.withPayload("application/json");
    getUsers200Payload.withMediaType("application/json");
    final NodeShape getUsers200Schema = getUsers200Payload.withObjectSchema("schema");
    getUsers200Schema.withClosed(false);

    final ScalarShape userNameSchema = new ScalarShape();
    userNameSchema.withDataType("http://www.w3.org/2001/XMLSchema#string");
    final PropertyShape userName = getUsers200Schema.withProperty("username");
    userName
      .withMinCount(1)
      .withPath("http://a.ml/vocabularies/data#name")
      .withRange(userNameSchema);

    final ScalarShape userEmailSchema = new ScalarShape();
    userEmailSchema.withDataType("http://www.w3.org/2001/XMLSchema#string");
    final PropertyShape userEmail = getUsers200Schema.withProperty("email");
    userEmail
      .withMinCount(1)
      .withPath("http://a.ml/vocabularies/data#email")
      .withRange(userEmailSchema);

    final EndPoint user = api.withEndPoint("/users/{id}");
    user
        .withName("User endpoint")
        .withDescription("Get user");

    Document model = new Document(api);
    final String generated = Raml10.generateString(model).get();
    System.out.println("Generated from constructed: " + generated);
  }
}
