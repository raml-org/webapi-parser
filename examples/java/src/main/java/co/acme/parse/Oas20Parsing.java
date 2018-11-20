package co.acme.parse;

import amf.client.AMF;
import amf.client.model.document.BaseUnit;
import amf.client.model.document.Document;
import amf.client.parse.Oas20Parser;

import java.util.concurrent.ExecutionException;

public class Oas20Parsing {

  public static void parseFileWithFuture() throws InterruptedException, ExecutionException {
    //#oas-20-parse-file-future
    AMF.init().get();

    /* Parsing Oas 20 with specified file returning future. */
    final BaseUnit result = new Oas20Parser().parseFileAsync("file://src/main/resources/examples/banking-api.json").get();
    System.out.println("Expected unit encoding webapi: " + ((Document) result).encodes());
    //#oas-20-parse-file-future
  }

  public static void parseStringWithFuture() throws InterruptedException, ExecutionException {
    //#oas-20-parse-string-future
    AMF.init().get();

    /* Parsing Oas 20 with specified content. */
    final BaseUnit result = new Oas20Parser().parseStringAsync(
            "{\n" +
            "  \"swagger\": \"2.0\",\n" +
            "  \"info\": {\n" +
            "    \"title\": \"ACME Banking HTTP API\",\n" +
            "    \"version\": \"1.0\"\n" +
            "  },\n" +
            "  \"host\": \"acme-banking.com\"" +
            "}").get();
    System.out.println("Expected unit encoding webapi: " + ((Document) result).encodes());
    //#oas-20-parse-string-future
  }
}