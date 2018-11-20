package co.acme.parse;

import amf.client.AMF;
import amf.client.model.document.BaseUnit;
import amf.client.model.document.Document;
import amf.client.parse.Raml10Parser;

import java.util.concurrent.ExecutionException;

public class Raml10Parsing {

  public static void parseFileWithFuture() throws InterruptedException, ExecutionException {
    //#raml-10-parse-file-future
    AMF.init().get();

    /* Parsing Raml 10 with specified file returning future. */
    final BaseUnit result = new Raml10Parser().parseFileAsync("file://src/main/resources/examples/banking-api.raml").get();
    System.out.println("Expected unit encoding webapi: " + ((Document) result).encodes());
    //#raml-10-parse-file-future
  }
  
  public static void parseStringWithFuture() throws InterruptedException, ExecutionException {
    //#raml-10-parse-string-future
    AMF.init().get();

    /* Parsing Raml 10 with specified content. */
    final BaseUnit result = new Raml10Parser().parseStringAsync(
            "#%RAML 1.0\n" +
            "\n" +
            "title: ACME Banking HTTP API\n" +
            "version: 1.0").get();
    System.out.println("Expected unit encoding webapi: " + ((Document) result).encodes());
    //#raml-10-parse-string-future
  }
  
}