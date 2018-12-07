package co.acme.parse;

import webapi.Raml08;
import amf.client.model.document.BaseUnit;
import amf.client.model.document.Document;
import amf.client.model.domain.WebApi;
import amf.client.model.domain.EndPoint;

import java.util.concurrent.ExecutionException;

public class Raml08Parsing {

  public static void parseFile() throws InterruptedException, ExecutionException {
    final BaseUnit result = Raml08.parse("file://../api-specs/raml/magic-api.raml").get();
    System.out.println("Parsed Raml08 file. Expected unit encoding webapi: " + ((Document) result).encodes());
  }

  /* Parses string and modifies API via AMF model */
  public static void parseString() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 0.8\n" +
                "\n" +
                "title: ACME Banking HTTP API\n" +
                "version: 1.0";
    System.out.println("Input Raml08 string:\n" + inp);
    Document doc = (Document) Raml08.parse(inp).get();

    // Modify model
    WebApi api = (WebApi) doc.encodes();
    EndPoint edp = api.withEndPoint("/balance");
    edp.withOperation("get");
    edp.operations().get(0).withName("getBalance");

    String output = Raml08.generateString(doc).get();
    System.out.println("Generated Raml08 string:\n" + output);
  }
}
