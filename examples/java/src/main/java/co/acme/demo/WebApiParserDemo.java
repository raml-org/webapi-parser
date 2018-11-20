package co.acme.demo;

import co.acme.parse.Raml10Parsing;

import java.util.concurrent.ExecutionException;

public class WebApiParserDemo {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Raml10Parsing.parseString();
    // Raml10Parsing.parseFile();
  }
}