package co.acme.parse;

import webapi.Raml10;
import amf.client.model.document.BaseUnit;
import amf.client.model.document.Document;

import java.util.concurrent.ExecutionException;

public class Raml10Parsing {

  // public static void parseFile() throws InterruptedException, ExecutionException {
  //   AMF.init().get();

  //   final BaseUnit result = new Raml10Parser().parseFileAsync("file://src/main/resources/examples/banking-api.raml").get();
  //   System.out.println("Expected unit encoding webapi: " + ((Document) result).encodes());
  // }

  public static void parseString() throws InterruptedException, ExecutionException {
    final BaseUnit result = Raml10.parseString(
            "#%RAML 1.0\n" +
            "\n" +
            "title: ACME Banking HTTP API\n" +
            "version: 1.0").get();
    System.out.println("Expected unit encoding webapi: " + ((Document) result).encodes());
  }
}
