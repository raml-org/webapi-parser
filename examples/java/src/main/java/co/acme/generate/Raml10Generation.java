package co.acme.generate;

import webapi.Raml10;
import webapi.WebApiBaseUnit;

import java.util.concurrent.ExecutionException;

public class Raml10Generation {

  // Example of generating RAML 1.0 file
  public static void generateFile() throws InterruptedException, ExecutionException {
    // Parse RAML 1.0 file to get WebApi Model
    final WebApiBaseUnit result = Raml10.parse("file://../api-specs/raml/api-with-types.raml").get();

    // Resolve parsed model (optional)
    final WebApiBaseUnit resolved = Raml10.resolve(result).get();

    String fpath = "file://generated.raml";
    // Generate RAML 1.0 file
    Raml10.generateFile(resolved, fpath).get();
    System.out.println("Generating Raml10 file at: " + fpath);
  }

  // Example of generating RAML 1.0 string
  public static void generateString() throws InterruptedException, ExecutionException {
    // Parse RAML 1.0 file to get WebApi Model
    final WebApiBaseUnit result = Raml10.parse("file://../api-specs/raml/api-with-types.raml").get();

    // Generate RAML 1.0 string
    final String output = Raml10.generateString(result).get();
    System.out.println("Generating Raml10 string: " + output);
  }
}
