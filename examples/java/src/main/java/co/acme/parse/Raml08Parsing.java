// package co.acme.parse;

// import amf.client.AMF;
// import amf.client.model.document.BaseUnit;
// import amf.client.model.document.Document;
// import amf.client.parse.Raml08Parser;

// import java.util.concurrent.ExecutionException;

// public class Raml08Parsing {

//   public static void parseFileWithFuture() throws InterruptedException, ExecutionException {
//     //#raml-08-parse-file-future
//     AMF.init().get();

//     /* Parsing Raml 08 with specified file returning future. */
//     final BaseUnit result = new Raml08Parser().parseFileAsync("file://src/main/resources/examples/banking-api-08.raml").get();
//     System.out.println("Expected unit encoding webapi: " + ((Document) result).encodes());
//     //#raml-08-parse-file-future
//   }

//   public static void parseStringWithFuture() throws InterruptedException, ExecutionException {
//     //#raml-08-parse-string-future
//     AMF.init().get();

//     /* Parsing Raml 08 with specified content. */
//     final BaseUnit result = new Raml08Parser().parseStringAsync(
//             "#%RAML 0.8\n" +
//             "\n" +
//             "title: ACME Banking HTTP API\n" +
//             "version: 1.0").get();
//     System.out.println("Expected unit encoding webapi: " + ((Document) result).encodes());
//     //#raml-08-parse-string-future
//   }
// }
