package co.acme.generate;

import webapi.AmfGraph;
import amf.client.model.document.BaseUnit;

import java.util.concurrent.ExecutionException;

public class AmfGraphGeneration {

  public static void generateFile() throws InterruptedException, ExecutionException {
    final BaseUnit result = AmfGraph.parseFile("file://../api-specs/amf-graph/api-with-types.json").get();

    String fpath = "file://generated.json";
    AmfGraph.generateFile(result, fpath).get();
    System.out.println("Generating AmfGraph file at: " + fpath);
  }

  public static void generateString() throws InterruptedException, ExecutionException {
    final BaseUnit result = AmfGraph.parseFile("file://../api-specs/amf-graph/api-with-types.json").get();

    final String output = AmfGraph.generateString(result).get();
    System.out.println("Generating AmfGraph string: " + output);
  }
}
