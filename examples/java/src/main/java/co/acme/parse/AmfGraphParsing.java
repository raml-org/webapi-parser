package co.acme.parse;

import webapi.AmfGraph;
import amf.client.model.document.BaseUnit;
import amf.client.model.document.Document;

import java.util.concurrent.ExecutionException;

public class AmfGraphParsing {

  public static void parseFile() throws InterruptedException, ExecutionException {
    final BaseUnit result = AmfGraph.parseFile("file://../api-specs/amf-graph/api-with-types.json").get();
    System.out.println("Parsed AMF Graph file. Expected unit encoding webapi: " + ((Document) result).encodes());
  }

  public static void parseString() throws InterruptedException, ExecutionException {
    final BaseUnit result = AmfGraph.parseString(
            "[\n" +
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
                    "}]}]").get();
    System.out.println("Parsed AMF Graph string. Expected unit encoding webapi: " + ((Document) result).encodes());
  }
}