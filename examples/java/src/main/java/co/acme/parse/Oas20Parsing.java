package co.acme.parse;

import webapi.Oas20;
import amf.client.model.document.BaseUnit;
import amf.client.model.document.Document;
import amf.client.model.domain.WebApi;
import amf.client.model.domain.Operation;
import amf.client.model.domain.EndPoint;

import java.util.concurrent.ExecutionException;
import java.util.Arrays;

public class Oas20Parsing {

  public static void parseFile() throws InterruptedException, ExecutionException {
    final BaseUnit result = Oas20.parse("file://../api-specs/oas/api-with-types.json").get();
    System.out.println("Parsed Oas20 JSON file. Expected unit encoding webapi: " + ((Document) result).encodes());
  }

  /* Parses string and modifies API via AMF model */
  public static void parseString() throws InterruptedException, ExecutionException {
    String inp ="{\n" +
                "  \"openapi\": \"2.0\",\n" +
                "  \"info\": {\n" +
                "    \"title\": \"ACME Banking HTTP API\",\n" +
                "    \"version\": \"1.0\"\n" +
                "  },\n" +
                "  \"host\": \"acme-banking.com\"" +
                "}";
    System.out.println("Input Oas20 JSON string:\n" + inp);
    Document doc = (Document) Oas20.parse(inp).get();

    // Modify model
    WebApi api = (WebApi) doc.encodes();
    api.withAccepts(Arrays.asList("application/json"));
    api.withContentType(Arrays.asList("application/json+ld"));

    String output = Oas20.generateString(doc).get();
    System.out.println("Generated Oas20 JSON string:\n" + output);
  }

  public static void parseYamlFile() throws InterruptedException, ExecutionException {
    final BaseUnit result = Oas20.parseYaml("file://../api-specs/oas/api-with-types.yaml").get();
    System.out.println("Parsed Oas20 YAML file. Expected unit encoding webapi: " + ((Document) result).encodes());
  }

  /* Parses string and modifies API via AMF model */
  public static void parseYamlString() throws InterruptedException, ExecutionException {
    String oasYaml = "openapi: '2.0'\n" +
        "info:\n" +
          "title: API with Types\n" +
          "version: ''";
    System.out.println("Input Oas20 YAML string:\n" + oasYaml);
    Document doc = (Document) Oas20.parseYaml(oasYaml).get();

    // Modify model
    WebApi api = (WebApi) doc.encodes();
    EndPoint edp = api.withEndPoint("/books");
    Operation getBooks = edp.withOperation("get");
    getBooks.withName("getBooks");
    getBooks.withResponse("200");

    String output = Oas20.generateString(doc).get();
    System.out.println("Generated Oas20 JSON string:\n" + output);
  }
}