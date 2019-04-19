package co.acme.generate;

import webapi.Oas20;
import webapi.WebApiBaseUnit;

import java.util.concurrent.ExecutionException;

public class Oas20Generation {

  // Example of generating OAS 2.0 file
  public static void generateFile() throws InterruptedException, ExecutionException {
    // Parse OAS 2.0 file to get WebApi Model
    final WebApiBaseUnit result = Oas20.parse("file://../api-specs/oas/api-with-types.json").get();

    // Resolve parsed model (optional)
    final WebApiBaseUnit resolved = Oas20.resolve(result).get();

    String fpath = "file://generated.json";

    // Generate OAS 2.0 file
    Oas20.generateFile(resolved, fpath).get();
    System.out.println("Generating Oas20 file at: " + fpath);
  }

  // Example of generating OAS 2.0 string
  public static void generateString() throws InterruptedException, ExecutionException {
    // Parse OAS 2.0 file to get WebApi Model
    final WebApiBaseUnit result = Oas20.parse("file://../api-specs/oas/api-with-types.json").get();

    // Generate OAS 2.0 string
    final String output = Oas20.generateString(result).get();
    System.out.println("Generating Oas20 string: " + output);
  }
}
