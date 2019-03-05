package co.acme.parse;

import webapi.Raml08;
import amf.client.model.document.BaseUnit;
import amf.client.model.document.Document;
import amf.client.model.domain.WebApi;
import amf.client.model.domain.EndPoint;

import java.util.concurrent.ExecutionException;

public class Raml08Parsing {

  // Example of parsing RAML 0.8 file
  public static void parseFile() throws InterruptedException, ExecutionException {
    // Parse the file
    final BaseUnit result = Raml08.parse("file://../api-specs/raml/magic-api.raml").get();

    // Log parsed model API
    System.out.println("Parsed Raml08 file. Expected unit encoding webapi: " + ((Document) result).encodes());
  }

  // Example of parsing RAML 0.8 string
  public static void parseString() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 0.8\n" +
                "\n" +
                "title: ACME Banking HTTP API\n" +
                "version: 1.0";
    System.out.println("Input Raml08 string:\n" + inp);

    // Parse the string
    Document doc = (Document) Raml08.parse(inp).get();

    // Get the API instance
    WebApi api = (WebApi) doc.encodes();

    // Create new endpoint /balance
    EndPoint edp = api.withEndPoint("/balance");

    // Add method GET to endpoint /balance
    edp.withOperation("get");

    // Set title of GET method of /balance
    edp.operations().get(0).withName("getBalance");

    // Generate RAML 0.8 from updated model and log it
    String output = Raml08.generateString(doc).get();
    System.out.println("Generated Raml08 string:\n" + output);
  }
}
