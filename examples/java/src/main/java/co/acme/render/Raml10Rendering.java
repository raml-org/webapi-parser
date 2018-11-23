// package co.acme.render;

// import amf.client.AMF;
// import amf.client.model.document.BaseUnit;
// import amf.client.parse.Raml10Parser;
// import amf.client.render.Raml10Renderer;

// import java.io.File;
// import java.io.IOException;
// import java.util.concurrent.ExecutionException;

// public class Raml10Rendering {

//   public static void renderFileWithFuture() throws InterruptedException, ExecutionException, IOException {
//     //#raml-10-render-file-future
//     AMF.init().get();

//     /* Parsing Raml 10 with specified file returning future. */
//     final BaseUnit result = new Raml10Parser().parseFileAsync("file://src/main/resources/examples/banking-api.raml").get();

//     final File output = File.createTempFile("raml-", "output.raml");
//     new Raml10Renderer().generateFile(result, output).get();
//     System.out.println("Rendering output at: " + output.getAbsolutePath());
//     //#raml-10-render-file-future
//   }

//   public static void renderStringWithFuture() throws InterruptedException, ExecutionException {
//     //#raml-10-render-string-future
//     AMF.init().get();

//     /* Parsing Raml 10 with specified file returning future. */
//     final BaseUnit result = new Raml10Parser().parseFileAsync("file://src/main/resources/examples/banking-api.raml").get();

//     final String output = new Raml10Renderer().generateString(result).get();
//     System.out.println("Rendering output: " + output);
//     //#raml-10-render-string-future
//   }
// }
