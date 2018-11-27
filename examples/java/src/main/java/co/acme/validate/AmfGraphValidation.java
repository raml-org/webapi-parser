package co.acme.validate;

import webapi.AmfGraph;
import amf.client.model.document.BaseUnit;
import amf.client.validate.ValidationReport;

import java.util.concurrent.ExecutionException;

public class AmfGraphValidation {

  public static void validate() throws InterruptedException, ExecutionException {
    final BaseUnit result = AmfGraph.parse("file://../api-specs/amf-graph/api-with-types-invalid.json").get();

    final ValidationReport report = AmfGraph.validate(result).get();
    System.out.println("AmfGraph validation report: " + report);
  }
}
