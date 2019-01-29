package co.acme.demo;

import co.acme.parse.Raml10Parsing;
import co.acme.parse.Raml08Parsing;
import co.acme.parse.Oas20Parsing;
import co.acme.parse.AmfGraphParsing;
import co.acme.generate.Raml10Generation;
import co.acme.generate.Raml08Generation;
import co.acme.generate.Oas20Generation;
import co.acme.generate.AmfGraphGeneration;
import co.acme.validate.Raml10Validation;
import co.acme.validate.Raml08Validation;
import co.acme.validate.Oas20Validation;
import co.acme.validate.AmfGraphValidation;
import co.acme.model.Raml10Building;
import co.acme.model.Raml10Navigation;

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

    Raml10Generation.generateString();
    Raml10Generation.generateFile();

    Raml08Generation.generateString();
    Raml08Generation.generateFile();

    Oas20Generation.generateString();
    Oas20Generation.generateFile();

    AmfGraphGeneration.generateString();
    AmfGraphGeneration.generateFile();

    Raml10Validation.validate();
    Raml08Validation.validate();
    Oas20Validation.validate();
    AmfGraphValidation.validate();

    Raml10Building.buildApi();
    Raml10Navigation.navigateApi();
  }
}