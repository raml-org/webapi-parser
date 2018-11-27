package co.acme.validate;

import webapi.Raml08;
import amf.client.model.document.BaseUnit;
import amf.client.validate.ValidationReport;

import java.util.concurrent.ExecutionException;

public class Raml08Validation {

  public static void validate() throws InterruptedException, ExecutionException {
    final BaseUnit result = Raml08.parse("file://../api-specs/raml/invalid-examples-08.raml").get();

    final ValidationReport report = Raml08.validate(result).get();
    System.out.println("Raml08 validation report: " + report);
  }
}
