package co.acme.validate;

import webapi.Raml10;
import amf.client.model.document.BaseUnit;
import amf.client.validate.ValidationReport;

import java.util.concurrent.ExecutionException;

public class Raml10Validation {

  public static void validate() throws InterruptedException, ExecutionException {
    final BaseUnit result = Raml10.parse("file://../api-specs/raml/invalid-examples.raml").get();

    final ValidationReport report = Raml10.validate(result).get();
    System.out.println("Raml10 validation report: " + report);
  }
}
