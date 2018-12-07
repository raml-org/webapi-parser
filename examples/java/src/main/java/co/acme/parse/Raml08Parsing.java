package co.acme.parse;

import webapi.Raml08;
import amf.client.model.document.BaseUnit;
import amf.client.model.document.Document;

import java.util.concurrent.ExecutionException;

public class Raml08Parsing {

  public static void parseFile() throws InterruptedException, ExecutionException {
    final BaseUnit result = Raml08.parse("file://../api-specs/raml/magic-api.raml").get();
    System.out.println("Parsed Raml08 file. Expected unit encoding webapi: " + ((Document) result).encodes());
  }

  public static void parseString() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 0.8\n" +
                "\n" +
                "title: ACME Banking HTTP API\n" +
                "version: 1.0";
    System.out.println("Input Raml08 string:\n" + inp);
    final BaseUnit result = Raml08.parse(inp).get();
    System.out.println("Output Raml08 string:\n" + result.raw().get());
  }
}
