package co.acme.parse;

import webapi.Oas20;
import webapi.WebApiBaseUnit;
import webapi.WebApiDocument;
import amf.client.model.domain.WebApi;
import amf.client.model.domain.Operation;
import amf.client.model.domain.EndPoint;

import java.util.concurrent.ExecutionException;
import java.util.Arrays;

public class Oas20Parsing {

  // Example of parsing OAS 2.0 JSON file
  public static void parseFile() throws InterruptedException, ExecutionException {
    // Parse the file
    final WebApiBaseUnit result = Oas20.parse("file://../api-specs/oas/api-with-types.json").get();

    // Log parsed model API
    System.out.println("Parsed Oas20 JSON file. Expected unit encoding webapi: " + ((WebApiDocument) result).encodes());
  }

  // Example of parsing and modifying OAS 2.0 JSON string
  public static void parseString() throws InterruptedException, ExecutionException {
    String inp ="{\n" +
                "  \"swagger\": \"2.0\",\n" +
                "  \"info\": {\n" +
                "    \"title\": \"ACME Banking HTTP API\",\n" +
                "    \"version\": \"1.0\"\n" +
                "  },\n" +
                "  \"host\": \"acme-banking.com\"" +
                "}";
    System.out.println("Input Oas20 JSON string:\n" + inp);

    // Parse the string and convert it to WebApiDocument
    WebApiDocument doc = (WebApiDocument) Oas20.parse(inp).get();

    // Get the API instance
    WebApi api = (WebApi) doc.encodes();

    // Set API 'accepts' param
    api.withAccepts(Arrays.asList("application/json"));

    // Set API content type
    api.withContentType(Arrays.asList("application/json+ld"));

    // Generate OAS 2.0 string and log it
    String output = Oas20.generateString(doc).get();
    System.out.println("Generated Oas20 JSON string:\n" + output);
  }

  // Example of parsing OAS 2.0 YAML file
  public static void parseYamlFile() throws InterruptedException, ExecutionException {
    // Parse the file
    final WebApiBaseUnit result = Oas20.parseYaml("file://../api-specs/oas/api-with-types.yaml").get();

    // Log parsed model API
    System.out.println("Parsed Oas20 YAML file. Expected unit encoding webapi: " + ((WebApiDocument) result).encodes());
  }

  // Example of parsing and modifying OAS 2.0 YAML string
  public static void parseYamlString() throws InterruptedException, ExecutionException {
    String oasYaml = "swagger: '2.0'\n" +
        "info:\n" +
          "title: API with Types\n" +
          "version: ''";
    System.out.println("Input Oas20 YAML string:\n" + oasYaml);

    // Parse the string
    WebApiDocument doc = (WebApiDocument) Oas20.parseYaml(oasYaml).get();

    // Get the API instance
    WebApi api = (WebApi) doc.encodes();

    // Create new endpoint /books
    EndPoint edp = api.withEndPoint("/books");

    // Create new method GET at endpoint /books
    Operation getBooks = edp.withOperation("get");

    // Set title of GET /books
    getBooks.withName("getBooks");

    // Add 200 response to GET /books
    getBooks.withResponse("200");

    // Generate OAS 2.0 JSON string and log it
    String output = Oas20.generateString(doc).get();
    System.out.println("Generated Oas20 JSON string:\n" + output);
  }
}