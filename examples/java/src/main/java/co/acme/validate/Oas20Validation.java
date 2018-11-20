package co.acme.validate;

import amf.MessageStyles;
import amf.ProfileName;
import amf.ProfileNames;
import amf.client.AMF;
import amf.client.environment.DefaultEnvironment;
import amf.client.environment.Environment;
import amf.client.model.document.BaseUnit;
import amf.client.parse.Raml08Parser;
import amf.client.parse.Raml10Parser;
import amf.client.validate.ValidationReport;

import java.util.concurrent.ExecutionException;

public class Oas20Validation {

  public static ValidationReport validation() throws InterruptedException, ExecutionException {
    //#raml-10-webapi-validation
    AMF.init().get();

    /* Parsing Raml 10 with specified file returning future. */
    final BaseUnit result = new Raml10Parser().parseFileAsync("file://src/main/resources/examples/banking-api-error.raml").get();

    /* Run RAML default validations on parsed unit (expects one error). */
    final ValidationReport report = AMF.validate(result, ProfileNames.RAML(), MessageStyles.RAML()).get();
    System.out.println("report.conforms()? " + report.conforms());
    return report;
    //#raml-10-webapi-validation
  }
}
