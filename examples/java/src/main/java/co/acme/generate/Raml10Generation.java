package co.acme.generate;

import webapi.Raml10;
import amf.client.model.document.BaseUnit;

import java.util.concurrent.ExecutionException;

public class Raml10Generation {

  public static void generateFile() throws InterruptedException, ExecutionException {
    final BaseUnit result = Raml10.parseFile("file://../api-specs/raml/api-with-types.raml").get();

    // Optional step
    final BaseUnit resolved = Raml10.resolve(result).get();

    String fpath = "file://generated.raml";
    Raml10.generateFile(resolved, fpath).get();
    System.out.println("Generating Raml10 file at: " + fpath);
  }

  public static void generateString() throws InterruptedException, ExecutionException {
    final BaseUnit result = Raml10.parseFile("file://../api-specs/raml/api-with-types.raml").get();

    final String output = Raml10.generateString(result).get();
    System.out.println("Generating Raml10 string: " + output);
  }
}
