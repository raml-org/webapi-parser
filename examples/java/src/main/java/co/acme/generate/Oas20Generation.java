package co.acme.generate;

import webapi.Oas20;
import webapi.WebApiBaseUnit;

import java.util.concurrent.ExecutionException;

public class Oas20Generation {

  // Example of generating OAS 2.0 JSON file
  public static void generateFile() throws InterruptedException, ExecutionException {
    // Parse OAS 2.0 JSON file to get WebApi Model
    final WebApiBaseUnit result = Oas20.parse("file://../api-specs/oas20/api-with-types.json").get();

    // Resolve parsed model (optional)
    final WebApiBaseUnit resolved = Oas20.resolve(result).get();

    String fpath = "file://generated.json";

    // Generate OAS 2.0 JSON file
    Oas20.generateFile(resolved, fpath).get();
    System.out.println("Generating Oas20 JSON file at: " + fpath);
  }

  // Example of generating OAS 2.0 JSON string
  public static void generateString() throws InterruptedException, ExecutionException {
    // Parse OAS 2.0 JSON file to get WebApi Model
    final WebApiBaseUnit result = Oas20.parse("file://../api-specs/oas20/api-with-types.json").get();

    // Generate OAS 2.0 JSON string
    final String output = Oas20.generateString(result).get();
    System.out.println("Generating Oas20 JSON string: " + output);
  }

  // Example of generating OAS 2.0 YAML file
  public static void generateYamlFile() throws InterruptedException, ExecutionException {
    // Parse OAS 2.0 YAML file to get WebApi Model
    final WebApiBaseUnit result = Oas20.parseYaml("file://../api-specs/oas20/api-with-types.yaml").get();

    // Resolve parsed model (optional)
    final WebApiBaseUnit resolved = Oas20.resolve(result).get();

    String fpath = "file://generated.yaml";

    // Generate OAS 2.0 YAML file
    Oas20.generateYamlFile(resolved, fpath).get();
    System.out.println("Generating Oas20 YAML file at: " + fpath);
  }

  // Example of generating OAS 2.0 YAML string
  public static void generateYamlString() throws InterruptedException, ExecutionException {
    // Parse OAS 2.0 YAML file to get WebApi Model
    final WebApiBaseUnit result = Oas20.parseYaml("file://../api-specs/oas20/api-with-types.yaml").get();

    // Generate OAS 2.0 YAML string
    final String output = Oas20.generateYamlString(result).get();
    System.out.println("Generating Oas20 YAML string: " + output);
  }
}
