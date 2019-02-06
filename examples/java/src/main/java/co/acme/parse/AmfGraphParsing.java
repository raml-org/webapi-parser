package co.acme.parse;

import webapi.AmfGraph;
import amf.client.model.document.BaseUnit;
import amf.client.model.document.Document;

import java.util.concurrent.ExecutionException;

public class AmfGraphParsing {

  // Example of parsing AMF Graph file
  public static void parseFile() throws InterruptedException, ExecutionException {
    // Parse the file
    final BaseUnit result = AmfGraph.parse("file://../api-specs/amf-graph/api-with-types.json").get();

    // Log parsed model API
    System.out.println("Parsed AMF Graph file. Expected unit encoding webapi: " + ((Document) result).encodes());
  }

  // Example of parsing AMF Graph string
  public static void parseString() throws InterruptedException, ExecutionException {
    String inp ="[\n" +
                "  {\n" +
                "    \"@id\": \"\",\n" +
                "    \"@type\": [\n" +
                "      \"http://raml.org/vocabularies/document#Document\",\n" +
                "      \"http://raml.org/vocabularies/document#Fragment\",\n" +
                "      \"http://raml.org/vocabularies/document#Module\",\n" +
                "      \"http://raml.org/vocabularies/document#Unit\"\n" +
                "    ],\n" +
                "    \"http://raml.org/vocabularies/document#encodes\": [\n" +
                "      {\n" +
                "        \"@id\": \"#/web-api\",\n" +
                "        \"@type\": [\n" +
                "          \"http://schema.org/WebAPI\",\n" +
                "          \"http://raml.org/vocabularies/document#DomainElement\"\n" +
                "        ],\n" +
                "        \"http://schema.org/name\": [\n" +
                "          {\n" +
                "            \"@value\": \"ACME Banking HTTP API\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"http://raml.org/vocabularies/http#host\": [\n" +
                "          {\n" +
                "            \"@value\": \"acme-banking.com\"\n" +
                "          }\n" +
                "        ]" +
                "}]}]";
    System.out.println("Input AMF Graph string:\n" + inp);

    // Parse the string
    final BaseUnit result = AmfGraph.parse(inp).get();

    // Extract raw input from parsed model and log it
    System.out.println("Output AMF Graph string:\n" + result.raw().get());
  }
}