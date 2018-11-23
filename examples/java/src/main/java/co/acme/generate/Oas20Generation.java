package co.acme.generate;

import webapi.Oas20;
import amf.client.model.document.BaseUnit;

import java.util.concurrent.ExecutionException;

public class Oas20Generation {

  public static void generateFile() throws InterruptedException, ExecutionException {
    final BaseUnit result = Oas20.parseFile("file://../api-specs/oas/api-with-types.json").get();

    // Optional step
    final BaseUnit resolved = Oas20.resolve(result).get();

    String fpath = "file://generated.json";
    Oas20.generateFile(resolved, fpath).get();
    System.out.println("Generating Oas20 file at: " + fpath);
  }

  public static void generateString() throws InterruptedException, ExecutionException {
    final BaseUnit result = Oas20.parseFile("file://../api-specs/oas/api-with-types.json").get();

    final String output = Oas20.generateString(result).get();
    System.out.println("Generating Oas20 string: " + output);
  }
}
