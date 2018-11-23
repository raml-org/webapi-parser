package co.acme.demo;

import co.acme.parse.Raml10Parsing;
import co.acme.parse.Raml08Parsing;
import co.acme.parse.Oas20Parsing;
import co.acme.parse.AmfGraphParsing;

import java.util.concurrent.ExecutionException;

public class WebApiParserDemo {
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    Raml10Parsing.parseString();
    Raml10Parsing.parseFile();

    Raml08Parsing.parseString();
    Raml08Parsing.parseFile();

    Oas20Parsing.parseString();
    Oas20Parsing.parseFile();
    Oas20Parsing.parseYamlString();
    Oas20Parsing.parseYamlFile();

    AmfGraphParsing.parseString();
    AmfGraphParsing.parseFile();
  }
}