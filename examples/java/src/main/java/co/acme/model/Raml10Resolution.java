package co.acme.model;

import webapi.Raml10;
import webapi.WebApiDocument;

import java.util.concurrent.ExecutionException;

public class Raml10Resolution {

  // Example of resolving RAML 1.0 file
  public static void resolveApi() throws InterruptedException, ExecutionException {
    // Parse the file
    WebApiDocument doc = (WebApiDocument) Raml10.parse("file://../api-specs/raml/api-with-types.raml").get();

    // Resolve parsed model
    WebApiDocument resolved = (WebApiDocument) Raml10.resolve(doc).get();

    // Generate RAML 1.0 string from the resolved model and log it
    String output = Raml10.generateString(resolved).get();
    System.out.println("Generated Raml10 string:\n" + output);
  }
}
