package co.acme.validate;

import webapi.AmfGraph;
import webapi.WebApiBaseUnit;
import amf.client.validate.ValidationReport;

import java.util.concurrent.ExecutionException;

public class AmfGraphValidation {

  // Example of validating AMF Graph content parsed from a file
  public static void validate() throws InterruptedException, ExecutionException {
    // Parse invalid file
    final WebApiBaseUnit result = AmfGraph.parse("file://../api-specs/amf-graph/api-with-types-invalid.json").get();

    // Perform validation and get validation report
    final ValidationReport report = AmfGraph.validate(result).get();

    // Log validation report
    System.out.println("AmfGraph validation report: " + report);
  }
}
