package co.acme.validate;

import webapi.Raml10;
import webapi.WebApiBaseUnit;
import amf.client.validate.ValidationReport;

import java.util.concurrent.ExecutionException;

public class Raml10Validation {

  // Example of validating RAML 1.0 content parsed from a file
  public static void validate() throws InterruptedException, ExecutionException {
    // Parse invalid file
    final WebApiBaseUnit result = Raml10.parse("file://../api-specs/raml/invalid-examples.raml").get();

    // Perform validation and get validation report
    final ValidationReport report = Raml10.validate(result).get();

    // Log validation report
    System.out.println("Raml10 validation report: " + report);
  }
}
