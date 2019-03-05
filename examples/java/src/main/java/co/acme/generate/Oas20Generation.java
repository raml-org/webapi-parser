package co.acme.generate;

import webapi.Oas20;
import amf.client.model.document.BaseUnit;

import java.util.concurrent.ExecutionException;

public class Oas20Generation {

  // Example of generating OAS 2.0 file
  public static void generateFile() throws InterruptedException, ExecutionException {
    // Parse OAS 2.0 file to get AMF Model
    final BaseUnit result = Oas20.parse("file://../api-specs/oas/api-with-types.json").get();

    // Resolve parsed model (optional)
    final BaseUnit resolved = Oas20.resolve(result).get();

    String fpath = "file://generated.json";

    // Generate OAS 2.0 file
    Oas20.generateFile(resolved, fpath).get();
    System.out.println("Generating Oas20 file at: " + fpath);
  }

  // Example of generating OAS 2.0 string
  public static void generateString() throws InterruptedException, ExecutionException {
    // Parse OAS 2.0 file to get AMF Model
    final BaseUnit result = Oas20.parse("file://../api-specs/oas/api-with-types.json").get();

    // Generate OAS 2.0 string
    final String output = Oas20.generateString(result).get();
    System.out.println("Generating Oas20 string: " + output);
  }
}
