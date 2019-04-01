package co.acme.generate;

import webapi.AmfGraph;
import amf.client.model.document.BaseUnit;

import java.util.concurrent.ExecutionException;

public class AmfGraphGeneration {

  // Example of generating AMF Graph file
  public static void generateFile() throws InterruptedException, ExecutionException {
    // Parse AMF Graph file to get WebApi Model
    final BaseUnit result = AmfGraph.parse("file://../api-specs/amf-graph/api-with-types.json").get();

    // Resolve parsed model (optional)
    final BaseUnit resolved = AmfGraph.resolve(result).get();

    String fpath = "file://generated.json";

    // Generate AMF Graph file
    AmfGraph.generateFile(resolved, fpath).get();
    System.out.println("Generating AmfGraph file at: " + fpath);
  }

  // Example of generating AMF Graph string
  public static void generateString() throws InterruptedException, ExecutionException {
    // Parse AMF Graph file to get WebApi Model
    final BaseUnit result = AmfGraph.parse("file://../api-specs/amf-graph/api-with-types.json").get();

    // Generate AMF Graph string
    final String output = AmfGraph.generateString(result).get();
    System.out.println("Generating AmfGraph string: " + output);
  }
}
