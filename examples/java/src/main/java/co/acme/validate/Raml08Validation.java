package co.acme.validate;

import webapi.Raml08;
import webapi.WebApiBaseUnit;
import amf.client.validate.ValidationReport;

import java.util.concurrent.ExecutionException;

public class Raml08Validation {

  // Example of validating RAML 0.8 content parsed from a file
  public static void validate() throws InterruptedException, ExecutionException {
    // Parse invalid file
    final WebApiBaseUnit result = Raml08.parse("file://../api-specs/raml/invalid-examples-08.raml").get();

    // Perform validation and get validation report
    final ValidationReport report = Raml08.validate(result).get();

    // Log validation report
    System.out.println("Raml08 validation report: " + report);
  }
}
