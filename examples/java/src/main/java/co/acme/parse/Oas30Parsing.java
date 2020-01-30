package co.acme.parse;

import webapi.Oas30;
import webapi.WebApiBaseUnit;
import webapi.WebApiDocument;
import amf.client.model.domain.WebApi;
import amf.client.model.domain.Operation;
import amf.client.model.domain.EndPoint;

import java.util.concurrent.ExecutionException;
import java.util.Arrays;

public class Oas30Parsing {

  // Example of parsing OAS 3.0 JSON file
  public static void parseFile() throws InterruptedException, ExecutionException {
    // Parse the file
    final WebApiBaseUnit result = Oas30.parse("file://../api-specs/oas30/api-with-types.json").get();

    // Log parsed model API
    System.out.println("Parsed Oas30 JSON file. Expected unit encoding webapi: " + ((WebApiDocument) result).encodes());
  }

  // Example of parsing and modifying OAS 3.0 JSON string
  public static void parseString() throws InterruptedException, ExecutionException {
    String inp ="{\n" +
                "  \"openapi\": \"3.0.0\",\n" +
                "  \"info\": {\n" +
                "    \"title\": \"ACME Banking HTTP API\",\n" +
                "    \"version\": \"1.0\"\n" +
                "  }\n" +
                "}";
    System.out.println("Input Oas30 JSON string:\n" + inp);

    // Parse the string and convert it to WebApiDocument
    WebApiDocument doc = (WebApiDocument) Oas30.parse(inp).get();

    // Get the API instance
    WebApi api = (WebApi) doc.encodes();

    // Set API 'accepts' param
    api.withAccepts(Arrays.asList("application/json"));

    // Set API content type
    api.withContentType(Arrays.asList("application/json+ld"));

    // Generate OAS 3.0 string and log it
    String output = Oas30.generateString(doc).get();
    System.out.println("Generated Oas30 JSON string:\n" + output);
  }

  // Example of parsing OAS 3.0 YAML file
  public static void parseYamlFile() throws InterruptedException, ExecutionException {
    // Parse the file
    final WebApiBaseUnit result = Oas30.parseYaml("file://../api-specs/oas30/api-with-types.yaml").get();

    // Log parsed model API
    System.out.println("Parsed Oas30 YAML file. Expected unit encoding webapi: " + ((WebApiDocument) result).encodes());
  }

  // Example of parsing and modifying OAS 3.0 YAML string
  public static void parseYamlString() throws InterruptedException, ExecutionException {
    String oasYaml = "openapi: '3.0.0'\n" +
        "info:\n" +
          "title: API with Types\n" +
          "version: ''";
    System.out.println("Input Oas30 YAML string:\n" + oasYaml);

    // Parse the string
    WebApiDocument doc = (WebApiDocument) Oas30.parseYaml(oasYaml).get();

    // Get the API instance
    WebApi api = (WebApi) doc.encodes();

    // Create new endpoint /books
    EndPoint edp = api.withEndPoint("/books");

    // Create new method GET at endpoint /books
    Operation getBooks = edp.withOperation("get");

    // Set title of GET /books
    getBooks.withName("getBooks");

    // Add 300 response to GET /books
    getBooks.withResponse("300");

    // Generate OAS 3.0 JSON string and log it
    String output = Oas30.generateString(doc).get();
    System.out.println("Generated Oas30 JSON string:\n" + output);
  }
}