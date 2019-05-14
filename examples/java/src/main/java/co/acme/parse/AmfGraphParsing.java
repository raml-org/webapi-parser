package co.acme.parse;

import webapi.AmfGraph;
import webapi.WebApiBaseUnit;
import webapi.WebApiDocument;

import java.util.concurrent.ExecutionException;

public class AmfGraphParsing {

  // Example of parsing AMF Graph file
  public static void parseFile() throws InterruptedException, ExecutionException {
    // Parse the file
    final WebApiBaseUnit result = AmfGraph.parse("file://../api-specs/amf-graph/api-with-types.json").get();

    // Log parsed model API
    System.out.println("Parsed AMF Graph file. Expected unit encoding webapi: " + ((WebApiDocument) result).encodes());
  }

  // Example of parsing AMF Graph string
  public static void parseString() throws InterruptedException, ExecutionException {
    String inp ="[{\n" +
                "  \"@id\": \"http://a.ml/amf/default_document\",\n" +
                "  \"@type\": [\n" +
                "    \"http://a.ml/vocabularies/document#Document\",\n" +
                "    \"http://a.ml/vocabularies/document#Fragment\",\n" +
                "    \"http://a.ml/vocabularies/document#Module\",\n" +
                "    \"http://a.ml/vocabularies/document#Unit\"\n" +
                "  ],\n" +
                "  \"http://a.ml/vocabularies/document#encodes\": [{\n" +
                "    \"@id\": \"http://a.ml/amf/default_document#/web-api\",\n" +
                "    \"@type\": [\n" +
                "      \"http://schema.org/WebAPI\",\n" +
                "      \"http://a.ml/vocabularies/document#RootDomainElement\",\n" +
                "      \"http://a.ml/vocabularies/document#DomainElement\"\n" +
                "    ],\n" +
                "    \"http://schema.org/name\": [{\"@value\": \"API with Types\"}],\n" +
                "    \"http://a.ml/vocabularies/http#endpoint\": [{\n" +
                "      \"@id\": \"http://a.ml/amf/default_document#/web-api/end-points//users/{id}\",\n" +
                "      \"@type\": [\n" +
                "        \"http://a.ml/vocabularies/http#EndPoint\",\n" +
                "        \"http://a.ml/vocabularies/document#DomainElement\"\n" +
                "      ],\n" +
                "      \"http://a.ml/vocabularies/http#path\": [{\"@value\": \"/users/{id}\"}],\n" +
                "      \"http://www.w3.org/ns/hydra/core#supportedOperation\": [{\n" +
                "        \"@id\": \"http://a.ml/amf/default_document#/web-api/end-points//users/{id}/get\",\n" +
                "        \"@type\": [\n" +
                "          \"http://www.w3.org/ns/hydra/core#Operation\",\n" +
                "          \"http://a.ml/vocabularies/document#DomainElement\"\n" +
                "        ],\n" +
                "        \"http://www.w3.org/ns/hydra/core#method\": [{\"@value\": \"get\"}]\n" +
                "      }],\n" +
                "      \"http://a.ml/vocabularies/http#parameter\": [{\n" +
                "        \"@id\": \"http://a.ml/amf/default_document#/web-api/end-points//users/{id}/parameter/id\",\n" +
                "        \"@type\": [\n" +
                "          \"http://a.ml/vocabularies/http#Parameter\",\n" +
                "          \"http://a.ml/vocabularies/document#DomainElement\"\n" +
                "        ],\n" +
                "        \"http://schema.org/name\": [{\"@value\": \"id\"}],\n" +
                "        \"http://a.ml/vocabularies/http#paramName\": [{\"@value\": \"id\"}],\n" +
                "        \"http://www.w3.org/ns/hydra/core#required\": [{\"@value\": true}],\n" +
                "        \"http://a.ml/vocabularies/http#binding\": [{\"@value\": \"path\"}],\n" +
                "        \"http://a.ml/vocabularies/http#schema\": [{\n" +
                "          \"@id\": \"http://a.ml/amf/default_document#/web-api/end-points//users/{id}/parameter/id/scalar/id\",\n" +
                "          \"@type\": [\n" +
                "            \"http://a.ml/vocabularies/shapes#ScalarShape\",\n" +
                "            \"http://www.w3.org/ns/shacl#Shape\",\n" +
                "            \"http://a.ml/vocabularies/shapes#Shape\",\n" +
                "            \"http://a.ml/vocabularies/document#DomainElement\"\n" +
                "          ],\n" +
                "          \"http://www.w3.org/ns/shacl#datatype\": [\n" +
                "            {\"@id\": \"http://www.w3.org/2001/XMLSchema#string\"}\n" +
                "          ],\n" +
                "          \"http://www.w3.org/ns/shacl#name\": [{\"@value\": \"id\"}]\n" +
                "        }]\n" +
                "      }]\n" +
                "    }]\n" +
                "  }]\n" +
                "}]";
    System.out.println("Input AMF Graph string:\n" + inp);

    // Parse the string
    final WebApiBaseUnit result = AmfGraph.parse(inp).get();

    // Extract raw input from parsed model and log it
    System.out.println("Output AMF Graph string:\n" + result.raw().get());
  }
}