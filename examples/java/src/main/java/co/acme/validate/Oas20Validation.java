package co.acme.validate;

import webapi.Oas20;
import webapi.WebApiBaseUnit;
import amf.client.validate.ValidationReport;

import java.util.concurrent.ExecutionException;

public class Oas20Validation {

  // Example of validating OAS 2.0 content parsed from a file
  public static void validate() throws InterruptedException, ExecutionException {
    // Parse invalid file
    final WebApiBaseUnit result = Oas20.parse("file://../api-specs/oas/api-with-types-invalid.json").get();

    // Perform validation and get validation report
    final ValidationReport report = Oas20.validate(result).get();

    // Log validation report
    System.out.println("Oas20 validation report: " + report);
  }
}
