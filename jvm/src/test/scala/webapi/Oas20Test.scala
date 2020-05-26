package webapi

import webapi.WebApiClientConverters._

import amf.client.model.domain.{WebApi}

import org.scalatest.{AsyncFunSuite, Matchers, Assertion}
import org.scalatest.Assertions._


class Oas20Test extends AsyncFunSuite with Matchers with WaitingFileReader {

  private val validFilePath     = "file://shared/src/test/resources/oas20/api-with-types.json"
  private val validFilePathYaml = "file://shared/src/test/resources/oas20/api-with-types.yaml"
  private val invalidFilePath   = "file://shared/src/test/resources/oas20/api-with-types-invalid.json"
  private val generatedJsonFilePath = s"${System.getProperty("java.io.tmpdir")}/generated-oas20.json"
  private val generatedYamlFilePath = s"${System.getProperty("java.io.tmpdir")}/generated-oas20.yaml"
  private val apiString: String =
    """{
      |"swagger": "2.0",
      |"info": {"title": "API with Types", "version": ""},
      |"definitions": {
      |  "User": {
      |    "type": "object",
      |    "properties": {
      |      "firstName": {"type": "string"},
      |      "lastName": {"type": "string"},
      |      "age": {"minimum": 0, "maximum": 99, "type": "integer"}
      |    },
      |    "required": ["firstName", "lastName", "age"]
      |  }
      |},
      |"paths": {
      |  "/users/{id}": {
      |    "get": {
      |      "operationId": "GET_users-id",
      |      "produces": ["application/json"],
      |      "responses": {
      |        "200": {
      |          "description": "",
      |          "schema": {"$ref": "#/definitions/User"}
      |        }
      |      }
      |    },
      |    "parameters": [{
      |      "type": "string",
      |      "in": "path",
      |      "name": "id",
      |      "required": true
      |    }]
      |  }
      |}
      |}""".stripMargin
  private val apiStringWithRef: String =
    """{
      |"swagger": "2.0",
      |"info": {"title": "API with Types", "version": ""},
      |"paths": {
      |  "/users/{id}": {
      |    "get": {
      |      "operationId": "GET_users-id",
      |      "produces": ["application/json"],
      |      "responses": {
      |        "200": {
      |          "description": "",
      |          "schema": {"$ref": "cat-schema.json"}
      |        }
      |      }
      |    }
      |  }
      |}
      |}""".stripMargin
  private val apiStringYaml: String =
    """swagger: '2.0'
      |info:
      |  title: API with Types
      |  version: ''
      |definitions:
      |  User:
      |    type: object
      |    properties:
      |      firstName:
      |        type: string
      |      lastName:
      |        type: string
      |      age:
      |        minimum: 0
      |        maximum: 99
      |        type: integer
      |    required:
      |    - firstName
      |    - lastName
      |    - age
      |paths:
      |  "/users/{id}":
      |    get:
      |      operationId: GET_users-id
      |      produces:
      |      - application/json
      |      responses:
      |        '200':
      |          description: ''
      |          schema:
      |            "$ref": "#/definitions/User"
      |    parameters:
      |    - type: string
      |      in: path
      |      name: id
      |      required: true""".stripMargin
  private val apiStringYamlWithRef: String =
    """swagger: '2.0'
      |info:
      |  title: API with Types
      |  version: ''
      |paths:
      |  "/users/{id}":
      |    get:
      |      operationId: GET_users-id
      |      produces:
      |      - application/json
      |      responses:
      |        '200':
      |          description: ''
      |          schema:
      |            "$ref": "cat-schema.yaml"
      |      required: true""".stripMargin

  test("JSON string parsing with reference and baseUrl param") {
    for {
      unit <- Oas20.parse(
        apiStringWithRef,
        "file://shared/src/test/resources/includes/api.json").asInternal
      resolved <- Oas20.resolve(unit).asInternal
      genStr <- Oas20.generateString(resolved).asInternal
    } yield {
      val doc = unit.asInstanceOf[WebApiDocument]
      doc.location should be ("file://shared/src/test/resources/includes/api.json")
      genStr should not include ("$ref")
      genStr should include ("The cat's name")
    }
  }

  test("JSON string parsing with reference and no baseUrl param") {
    for {
      unit <- Oas20.parse(apiStringWithRef).asInternal
      resolved <- Oas20.resolve(unit).asInternal
      genStr <- Oas20.generateString(resolved).asInternal
    } yield {
      val doc = unit.asInstanceOf[WebApiDocument]
      doc.location should be ("http://a.ml/amf/default_document")
      genStr should not include ("$ref")
      genStr should not include ("The cat's name")
    }
  }

  test("JSON string parsing") {
    for {
      unit <- Oas20.parse(apiString).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("JSON file parsing") {
    for {
      unit <- Oas20.parse(validFilePath).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("JSON file generation") {
    for {
      unit <- Oas20.parse(apiString).asInternal
      _ <- Oas20.generateFile(unit, s"file://${generatedJsonFilePath}").asInternal
    } yield {
      val genStr = waitAndRead(generatedJsonFilePath)
      genStr should include ("definitions")
      genStr should include ("/users/{id}")
    }
  }

  test("JSON string generation") {
    for {
      unit <- Oas20.parse(apiString).asInternal
      genStr <- Oas20.generateString(unit).asInternal
    } yield {
      genStr should include ("definitions")
      genStr should include ("/users/{id}")
    }
  }

  test("Validation") {
    for {
      unit <- Oas20.parse(invalidFilePath).asInternal
      report <- Oas20.validate(unit).asInternal
    } yield {
      report.conforms should be (false)
      report.results should have size 1
    }
  }

  test("Resolution") {
    for {
      unit <- Oas20.parse(apiString).asInternal
      resolved <- Oas20.resolve(unit).asInternal
      genStr <- Oas20.generateString(resolved).asInternal
    } yield {
      genStr should not include ("$ref")
      genStr should not include ("definitions")
      genStr should not include ("User")
      genStr should include ("firstName")
      genStr should include ("schema")
    }
  }

  test("Resolution with preserving root definitions") {
    for {
      unit <- Oas20.parse(apiString).asInternal
      resolved <- Oas20.resolve(unit, true).asInternal
      genStr <- Oas20.generateString(resolved).asInternal
    } yield {
      genStr should not include ("$ref")
      genStr should include ("definitions")
      genStr should include ("User")
      genStr should include ("firstName")
      genStr should include ("schema")
    }
  }

  test("YAML string parsing with reference and baseUrl param") {
    for {
      unit <- Oas20.parseYaml(
        apiStringWithRef,
        "file://shared/src/test/resources/includes/api.yaml").asInternal
      resolved <- Oas20.resolve(unit).asInternal
      genStr <- Oas20.generateYamlString(resolved).asInternal
    } yield {
      val doc = unit.asInstanceOf[WebApiDocument]
      doc.location should be ("file://shared/src/test/resources/includes/api.yaml")
      genStr should not include ("$ref")
      genStr should include ("The cat's name")
    }
  }

  test("YAML string parsing with reference and no baseUrl param") {
    for {
      unit <- Oas20.parseYaml(apiStringWithRef).asInternal
      resolved <- Oas20.resolve(unit).asInternal
      genStr <- Oas20.generateYamlString(resolved).asInternal
    } yield {
      val doc = unit.asInstanceOf[WebApiDocument]
      doc.location should be ("http://a.ml/amf/default_document")
      genStr should not include ("$ref")
      genStr should not include ("The cat's name")
    }
  }

  test("YAML string parsing") {
    for {
      unit <- Oas20.parseYaml(apiStringYaml).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("YAML file parsing") {
    for {
      unit <- Oas20.parseYaml(validFilePathYaml).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("YAML file generation") {
    for {
      unit <- Oas20.parseYaml(apiStringYaml).asInternal
      _ <- Oas20.generateYamlFile(unit, s"file://${generatedYamlFilePath}").asInternal
    } yield {
      val genStr = waitAndRead(generatedYamlFilePath)
      genStr should include ("title: API with Types")
      genStr should include ("/users/{id}:")
    }
  }

  test("YAML string generation") {
    for {
      unit <- Oas20.parseYaml(apiStringYaml).asInternal
      genStr <- Oas20.generateYamlString(unit).asInternal
    } yield {
      genStr should include ("title: API with Types")
      genStr should include ("/users/{id}:")
    }
  }

  def assertApiParsed (unit: WebApiBaseUnit): Assertion = {
    val doc = unit.asInstanceOf[WebApiDocument]
    val api = doc.encodes.asInstanceOf[WebApi]
    doc.declares should have size 1
    api.endPoints should have size 1
    api.name.value() should be ("API with Types")
  }
}
