package co.acme.validate;

import webapi.Oas30;
import webapi.WebApiBaseUnit;
import amf.client.validate.ValidationReport;

import java.util.concurrent.ExecutionException;

public class Oas30Validation {

  // Example of validating OAS 3.0 content parsed from a file
  public static void validate() throws InterruptedException, ExecutionException {
    // Parse invalid file
    final WebApiBaseUnit result = Oas30.parse("file://../api-specs/oas30/api-with-types-invalid.json").get();

    // Perform validation and get validation report
    final ValidationReport report = Oas30.validate(result).get();

    // Log validation report
    System.out.println("Oas30 validation report: " + report);
  }
}
