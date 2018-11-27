package co.acme.validate;

import webapi.Oas20;
import amf.client.model.document.BaseUnit;
import amf.client.validate.ValidationReport;

import java.util.concurrent.ExecutionException;

public class Oas20Validation {

  public static void validate() throws InterruptedException, ExecutionException {
    final BaseUnit result = Oas20.parse("file://../api-specs/oas/api-with-types-invalid.json").get();

    final ValidationReport report = Oas20.validate(result).get();
    System.out.println("Oas20 validation report: " + report);
  }
}
