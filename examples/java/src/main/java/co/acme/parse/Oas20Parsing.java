package co.acme.parse;

import webapi.Oas20;
import amf.client.model.document.BaseUnit;
import amf.client.model.document.Document;

import java.util.concurrent.ExecutionException;

public class Oas20Parsing {

  public static void parseFile() throws InterruptedException, ExecutionException {
    final BaseUnit result = Oas20.parse("file://../api-specs/oas/api-with-types.json").get();
    System.out.println("Parsed Oas20 JSON file. Expected unit encoding webapi: " + ((Document) result).encodes());
  }

  public static void parseString() throws InterruptedException, ExecutionException {
    final BaseUnit result = Oas20.parse(
            "{\n" +
            "  \"swagger\": \"2.0\",\n" +
            "  \"info\": {\n" +
            "    \"title\": \"ACME Banking HTTP API\",\n" +
            "    \"version\": \"1.0\"\n" +
            "  },\n" +
            "  \"host\": \"acme-banking.com\"" +
            "}").get();
    System.out.println("Parsed Oas20 JSON string. Expected unit encoding webapi: " + ((Document) result).encodes());
  }

  public static void parseYamlFile() throws InterruptedException, ExecutionException {
    final BaseUnit result = Oas20.parseYaml("file://../api-specs/oas/api-with-types.yaml").get();
    System.out.println("Parsed Oas20 YAML file. Expected unit encoding webapi: " + ((Document) result).encodes());
  }

  public static void parseYamlString() throws InterruptedException, ExecutionException {
    String oasYaml = "swagger: '2.0'\n" +
        "info:\n" +
          "title: API with Types\n" +
          "version: ''";
    final BaseUnit result = Oas20.parseYaml(oasYaml).get();
    System.out.println("Parsed Oas20 YAML string. Expected unit encoding webapi: " + ((Document) result).encodes());
  }
}