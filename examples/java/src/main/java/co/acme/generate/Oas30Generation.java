package co.acme.generate;

import webapi.Oas30;
import webapi.WebApiBaseUnit;

import java.util.concurrent.ExecutionException;

public class Oas30Generation {

  // Example of generating OAS 3.0 JSON file
  public static void generateFile() throws InterruptedException, ExecutionException {
    // Parse OAS 3.0 JSON file to get WebApi Model
    final WebApiBaseUnit result = Oas30.parse("file://../api-specs/oas30/api-with-types.json").get();

    // Resolve parsed model (optional)
    final WebApiBaseUnit resolved = Oas30.resolve(result).get();

    String fpath = "file://generated.json";

    // Generate OAS 3.0 JSON file
    Oas30.generateFile(resolved, fpath).get();
    System.out.println("Generating Oas30 JSON file at: " + fpath);
  }

  // Example of generating OAS 3.0 JSON string
  public static void generateString() throws InterruptedException, ExecutionException {
    // Parse OAS 3.0 JSON file to get WebApi Model
    final WebApiBaseUnit result = Oas30.parse("file://../api-specs/oas30/api-with-types.json").get();

    // Generate OAS 3.0 JSON string
    final String output = Oas30.generateString(result).get();
    System.out.println("Generating Oas30 JSON string: " + output);
  }

  // Example of generating OAS 3.0 YAML file
  public static void generateYamlFile() throws InterruptedException, ExecutionException {
    // Parse OAS 3.0 YAML file to get WebApi Model
    final WebApiBaseUnit result = Oas30.parseYaml("file://../api-specs/oas30/api-with-types.yaml").get();

    // Resolve parsed model (optional)
    final WebApiBaseUnit resolved = Oas30.resolve(result).get();

    String fpath = "file://generated.yaml";

    // Generate OAS 3.0 YAML file
    Oas30.generateYamlFile(resolved, fpath).get();
    System.out.println("Generating Oas30 YAML file at: " + fpath);
  }

  // Example of generating OAS 3.0 YAML string
  public static void generateYamlString() throws InterruptedException, ExecutionException {
    // Parse OAS 3.0 YAML file to get WebApi Model
    final WebApiBaseUnit result = Oas30.parseYaml("file://../api-specs/oas30/api-with-types.yaml").get();

    // Generate OAS 3.0 YAML string
    final String output = Oas30.generateYamlString(result).get();
    System.out.println("Generating Oas30 YAML string: " + output);
  }
}
