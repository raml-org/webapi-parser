package co.acme.parse;

import webapi.Raml10;
import amf.client.model.document.BaseUnit;
import amf.client.model.document.Document;

import java.util.concurrent.ExecutionException;

public class Raml10Parsing {

  public static void parseFile() throws InterruptedException, ExecutionException {
    final BaseUnit result = Raml10.parse("file://../api-specs/raml/api-with-types.raml").get();
    System.out.println("Parsed Raml10 file. Expected unit encoding webapi: " + ((Document) result).encodes());
  }

  public static void parseString() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0\n" +
                "\n" +
                "title: ACME Banking HTTP API\n" +
                "version: 1.0";
    System.out.println("Input Raml10 string:\n" + inp);
    final BaseUnit result = Raml10.parse(inp).get();
    System.out.println("Output Raml10 string:\n" + result.raw().get());
  }
}
