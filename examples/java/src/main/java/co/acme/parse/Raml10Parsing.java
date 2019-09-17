package co.acme.parse;

import webapi.Raml10;
import webapi.WebApiBaseUnit;
import webapi.WebApiDocument;
import amf.client.model.domain.WebApi;

import java.util.concurrent.ExecutionException;

public class Raml10Parsing {

  // Example of parsing RAML 1.0 file
  public static void parseFile() throws InterruptedException, ExecutionException {
    // Parse the file
    final WebApiBaseUnit result = Raml10.parse("file://../api-specs/raml/api-with-types.raml").get();

    // Log parsed model API
    System.out.println("Parsed Raml10 file. Expected unit encoding webapi: " + ((WebApiDocument) result).encodes());
  }

  // Example of parsing RAML 1.0 string
  public static void parseString() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0\n" +
                "\n" +
                "title: ACME Banking HTTP API\n" +
                "version: 1.0";
    System.out.println("Input Raml10 string:\n" + inp);

    // Parse the string
    WebApiDocument doc = (WebApiDocument) Raml10.parse(inp).get();

    // Get parsed model API instance
    WebApi api = (WebApi) doc.encodes();

    // Set API version
    api.withVersion("3.7");

    // Set API description
    api.withDescription("Very nice api");

    // Generate RAML 1.0 string from updated model and log it
    String output = Raml10.generateString(doc).get();
    System.out.println("Generated Raml10 string:\n" + output);
  }

  // Example of parsing RAML 1.0 string with location param
  public static void parseStringWithLocation() throws InterruptedException, ExecutionException {
    String inp = "#%RAML 1.0\n" +
                 "title: API with Types\n" +
                 "/users/{id}:\n" +
                 "  get:\n" +
                 "    responses:\n" +
                 "      200:\n" +
                 "        body:\n" +
                 "          application/json:\n" +
                 "            type: !include cat-schema.json";
    String baseUrl = "file://../api-specs/includes/";
    System.out.println("Input Raml10 string:\n" + inp);

    // Parse the string
    WebApiDocument doc = (WebApiDocument) Raml10.parse(inp, baseUrl).get();

    System.out.println("Parsed content location:\n" + doc.location());

    // Generate RAML 1.0 string from updated model and log it
    String output = Raml10.generateString(doc).get();
    System.out.println("Generated Raml10 string:\n" + output);
  }
}
