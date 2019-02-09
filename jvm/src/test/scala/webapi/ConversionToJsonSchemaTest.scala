package webapi

import amf.client.convert.CoreClientConverters._

import org.scalatest.{AsyncFunSuite, Matchers}
import org.scalatest.Assertions._


class ConversionToJsonSchemaTest extends AsyncFunSuite with Matchers {

  private val ramlApi: String =
    """#%RAML 1.0
      |title: API with Types
      |types:
      |  User:
      |    type: object
      |    properties:
      |      firstName: string
      |      lastName:  string
      |      age:
      |        type: integer
      |        minimum: 0
      |        maximum: 99
      |  Book:
      |    type: object
      |    properties:
      |      title: string
      |      author: string""".stripMargin

  private val ramlLibrary: String =
    """#%RAML 1.0 Library
      |types:
      |  Book:
      |    type: object
      |    properties:
      |      title: string
      |      author: string
      |  User:
      |    type: object
      |    properties:
      |      firstName: string
      |      lastName:  string
      |      age:
      |        type: integer
      |        minimum: 0
      |        maximum: 99""".stripMargin

  private val ramlLibrarySingleType: String =
    """#%RAML 1.0 Library
      |types:
      |  Book:
      |    type: object
      |    properties:
      |      title: string
      |      author: string""".stripMargin

  private val ramlDataType: String =
    """#%RAML 1.0 DataType
      |type: object
      |properties:
      |  title: string
      |  author: string""".stripMargin

  private val jsonSchemaNoTypes: String =
    """{
      |  "$schema": "http://json-schema.org/draft-04/schema#",
      |  "definitions": {}
      |}""".stripMargin.replaceAll("\\s", "")

  private val jsonSchemaBook: String =
    """{
      |  "$schema": "http://json-schema.org/draft-04/schema#",
      |  "$ref": "#/definitions/Book",
      |  "definitions": {
      |    "Book": {
      |      "type": "object",
      |      "required": [
      |        "title",
      |        "author"
      |      ],
      |      "properties": {
      |        "title": {
      |          "type": "string"
      |        },
      |        "author": {
      |          "type": "string"
      |        }
      |      }
      |    }
      |  }
      |}""".stripMargin.replaceAll("\\s", "")

  test("Single type from API document") {
    for {
      converted <- Conversion.toJsonSchema(ramlApi, "Book").asInternal
    } yield {
      converted.replaceAll("\\s", "") should be (jsonSchemaBook)
    }
  }

  test("Single type from Library") {
    for {
      converted <- Conversion.toJsonSchema(ramlLibrary, "Book").asInternal
    } yield {
      converted.replaceAll("\\s", "") should be (jsonSchemaBook)
    }
  }

  test("Single type from Library that defines single type") {
    for {
      converted <- Conversion.toJsonSchema(ramlLibrarySingleType, "Book").asInternal
    } yield {
      converted.replaceAll("\\s", "") should be (jsonSchemaBook)
    }
  }

  test("Single type from DataType") {
    for {
      converted <- Conversion.toJsonSchema(ramlDataType, "Book").asInternal
    } yield {
      converted.replaceAll("\\s", "") should be (jsonSchemaBook)
    }
  }

  test("Skip inexisting types when converting one type") {
    for {
      converted <- Conversion.toJsonSchema(ramlApi, "InexistingType").asInternal
    } yield {
      converted.replaceAll("\\s", "") should be (jsonSchemaNoTypes)
    }
  }
}
