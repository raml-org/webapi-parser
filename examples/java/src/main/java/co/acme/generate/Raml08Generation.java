package co.acme.generate;

import webapi.Raml08;
import amf.client.model.document.BaseUnit;

import java.util.concurrent.ExecutionException;

public class Raml08Generation {

  // Example of generating RAML 0.8 file
  public static void generateFile() throws InterruptedException, ExecutionException {
    // Parse RAML 0.8 file to get AMF Model
    final BaseUnit result = Raml08.parse("file://../api-specs/raml/magic-api.raml").get();

    // Resolve parsed model (optional)
    final BaseUnit resolved = Raml08.resolve(result).get();

    String fpath = "file://generated.raml";
    // Generate RAML 0.8 file
    Raml08.generateFile(resolved, fpath).get();
    System.out.println("Generating Raml08 file at: " + fpath);
  }

  // Example of generating RAML 0.8 string
  public static void generateString() throws InterruptedException, ExecutionException {
    // Parse RAML 0.8 file to get AMF Model
    final BaseUnit result = Raml08.parse("file://../api-specs/raml/magic-api.raml").get();

    // Generate RAML 0.8 string
    final String output = Raml08.generateString(result).get();
    System.out.println("Generating Raml08 string: " + output);
  }
}
