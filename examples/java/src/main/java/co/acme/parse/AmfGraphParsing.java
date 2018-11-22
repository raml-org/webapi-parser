// package co.acme.parse;

// import amf.client.AMF;
// import amf.client.model.document.BaseUnit;
// import amf.client.model.document.Document;
// import amf.client.parse.AmfGraphParser;

// import java.util.concurrent.ExecutionException;

// public class AmfGraphParsing {

//   public static void parseFileWithFuture() throws InterruptedException, ExecutionException {
//     //#amf-parse-file-future
//     AMF.init().get();

//     /* Parsing AMF graph with specified file returning future. */
//     final BaseUnit result = new AmfGraphParser().parseFileAsync("file://src/main/resources/examples/banking-api.jsonld").get();
//     System.out.println("Expected unit encoding webapi: " + ((Document) result).encodes());
//     //#amf-parse-file-future
//   }

//   public static void parseStringWithFuture() throws InterruptedException, ExecutionException {
//     //#amf-parse-string-future
//     AMF.init().get();

//     /* Parsing AMF graph with specified content. */
//     final BaseUnit result = new AmfGraphParser().parseStringAsync(
//             "[\n" +
//                     "  {\n" +
//                     "    \"@id\": \"\",\n" +
//                     "    \"@type\": [\n" +
//                     "      \"http://raml.org/vocabularies/document#Document\",\n" +
//                     "      \"http://raml.org/vocabularies/document#Fragment\",\n" +
//                     "      \"http://raml.org/vocabularies/document#Module\",\n" +
//                     "      \"http://raml.org/vocabularies/document#Unit\"\n" +
//                     "    ],\n" +
//                     "    \"http://raml.org/vocabularies/document#encodes\": [\n" +
//                     "      {\n" +
//                     "        \"@id\": \"#/web-api\",\n" +
//                     "        \"@type\": [\n" +
//                     "          \"http://schema.org/WebAPI\",\n" +
//                     "          \"http://raml.org/vocabularies/document#DomainElement\"\n" +
//                     "        ],\n" +
//                     "        \"http://schema.org/name\": [\n" +
//                     "          {\n" +
//                     "            \"@value\": \"ACME Banking HTTP API\"\n" +
//                     "          }\n" +
//                     "        ],\n" +
//                     "        \"http://raml.org/vocabularies/http#host\": [\n" +
//                     "          {\n" +
//                     "            \"@value\": \"acme-banking.com\"\n" +
//                     "          }\n" +
//                     "        ]" +
//                     "}]}]").get();
//     System.out.println("Expected unit encoding webapi: " + ((Document) result).encodes());
//     //#amf-parse-string-future
//   }

//   public static void main(String[] args) throws ExecutionException, InterruptedException {
//     parseFileWithFuture();
//   }
// }