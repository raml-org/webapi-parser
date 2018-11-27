package co.acme.generate;

import webapi.Raml08;
import amf.client.model.document.BaseUnit;

import java.util.concurrent.ExecutionException;

public class Raml08Generation {

  public static void generateFile() throws InterruptedException, ExecutionException {
    final BaseUnit result = Raml08.parse("file://../api-specs/raml/magic-api.raml").get();

    // Optional step
    final BaseUnit resolved = Raml08.resolve(result).get();

    String fpath = "file://generated.raml";
    Raml08.generateFile(resolved, fpath).get();
    System.out.println("Generating Raml08 file at: " + fpath);
  }

  public static void generateString() throws InterruptedException, ExecutionException {
    final BaseUnit result = Raml08.parse("file://../api-specs/raml/magic-api.raml").get();

    final String output = Raml08.generateString(result).get();
    System.out.println("Generating Raml08 string: " + output);
  }
}
