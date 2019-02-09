package co.acme.convert;

import webapi.Conversion;

import java.util.concurrent.ExecutionException;

public class RamlDtToJsonSchema {

  // Example of converting single RAML Data Type from RAML 1.0 API file
  public static void convertFromApiFile() throws InterruptedException, ExecutionException {
    String inp = "file://../api-specs/raml/api-with-types.raml";
    String converted = Conversion.toJsonSchema(inp, "User").get();
    System.out.println("Converted to JSON Schema:\n" + converted);
  }

  // Example of converting single RAML Data Type from RAML 1.0 API string
  public static void convertFromApi() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0\n" +
                "title: API with Types\n" +
                "types:\n" +
                "  Book:\n" +
                "    type: object\n" +
                "    properties:\n" +
                "      title: string\n" +
                "      author: string\n" +
                "  User:\n" +
                "    type: object\n" +
                "    properties:\n" +
                "      username: string\n";
    System.out.println("Input Raml10 API:\n" + inp);
    String converted = Conversion.toJsonSchema(inp, "User").get();
    System.out.println("Converted to JSON Schema:\n" + converted);
  }

  // Example of converting single RAML Data Type from RAML 1.0 Library string
  public static void convertFromLibrary() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0 Library\n" +
                "types:\n" +
                "  Book:\n" +
                "    type: object\n" +
                "    properties:\n" +
                "      title: string\n" +
                "      author: string\n";
    System.out.println("Input Raml10 Library:\n" + inp);
    String converted = Conversion.toJsonSchema(inp, "Book").get();
    System.out.println("Converted to JSON Schema:\n" + converted);
  }

  // Example of converting single RAML Data Type from RAML 1.0 DataType Fragment string
  public static void convertFromDataType() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0 DataType\n" +
                "type: object\n" +
                "properties:\n" +
                "  title: string\n" +
                "  author: string\n";
    System.out.println("Input Raml10 DataType:\n" + inp);
    String converted = Conversion.toJsonSchema(inp, "AnyName").get();
    System.out.println("Converted to JSON Schema:\n" + converted);
  }

  // Example of converting inexisting type from RAML 1.0 library
  public static void convertInexisting() throws InterruptedException, ExecutionException {
    String inp ="#%RAML 1.0 Library\n" +
                "types:\n" +
                "  Book:\n" +
                "    type: object\n" +
                "    properties:\n" +
                "      title: string\n" +
                "      author: string\n";
    System.out.println("Input Raml10 Library:\n" + inp);
    try {
      String converted = Conversion.toJsonSchema(inp, "FOOBAR").get();
    } catch (Exception e) {
      System.out.println("Tried to convert inexisting type from API:\n" + e.toString());
    }
  }
}
