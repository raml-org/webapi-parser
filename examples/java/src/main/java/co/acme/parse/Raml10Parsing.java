package co.acme.parse;

import webapi.Raml10;
import amf.client.model.document.BaseUnit;
import amf.client.model.document.Document;
import amf.client.model.domain.WebApi;

import java.util.concurrent.ExecutionException;

public class Raml10Parsing {

  public static void parseFile() throws InterruptedException, ExecutionException {
    final BaseUnit result = Raml10.parse("file://../api-specs/raml/api-with-types.raml").get();
    System.out.println("Parsed Raml10 file. Expected unit encoding webapi: " + ((Document) result).encodes());
  }

  /* Parses string and modifies API via AMF model */
  public static void parseString() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0\n" +
                "\n" +
                "title: ACME Banking HTTP API\n" +
                "version: 1.0";
    System.out.println("Input Raml10 string:\n" + inp);
    Document doc = (Document) Raml10.parse(inp).get();

    // Modify model
    WebApi api = (WebApi) doc.encodes();
    api.withVersion("3.7");
    api.withDescription("Very nice api");

    String output = Raml10.generateString(doc).get();
    System.out.println("Generated Raml10 string:\n" + output);
  }
}
