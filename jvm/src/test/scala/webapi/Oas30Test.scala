package webapi

import webapi.WebApiClientConverters._

import amf.client.model.domain.{WebApi}

import org.scalatest.{AsyncFunSuite, Matchers, Assertion}
import org.scalatest.Assertions._


class Oas30Test extends AsyncFunSuite with Matchers with WaitingFileReader {

  private val validFilePath     = "file://shared/src/test/resources/oas30/api-with-types.json"
  private val validFilePathYaml = "file://shared/src/test/resources/oas30/api-with-types.yaml"
  private val invalidFilePath   = "file://shared/src/test/resources/oas30/api-with-types-invalid.json"
  private val generatedJsonFilePath = s"${System.getProperty("java.io.tmpdir")}/generated-oas30.json"
  private val generatedYamlFilePath = s"${System.getProperty("java.io.tmpdir")}/generated-oas30.yaml"
  private val apiString: String =
    """{
      |  "openapi": "3.0.0",
      |  "info": {
      |    "title": "API with Types",
      |    "version": ""
      |  },
      |  "paths": {
      |    "/users/{id}": {
      |      "get": {
      |        "operationId": "GET_users-id",
      |        "responses": {
      |          "200": {
      |            "description": "",
      |            "content": {
      |              "application/json": {
      |                "schema": {
      |                  "$ref": "#/components/schemas/User"
      |                }
      |              }
      |            }
      |          }
      |        }
      |      },
      |      "parameters": [
      |        {
      |          "in": "path",
      |          "name": "id",
      |          "required": true,
      |          "schema": {
      |            "type": "string"
      |          }
      |        }
      |      ]
      |    }
      |  },
      |  "components": {
      |    "schemas": {
      |      "User": {
      |        "type": "object",
      |        "required": [
      |          "firstName",
      |          "lastName",
      |          "age"
      |        ],
      |        "properties": {
      |          "firstName": {
      |            "type": "string"
      |          },
      |          "lastName": {
      |            "type": "string"
      |          },
      |          "age": {
      |            "minimum": 0,
      |            "maximum": 99,
      |            "type": "integer"
      |          }
      |        }
      |      }
      |    }
      |  }
      |}""".stripMargin
  private val apiStringWithRef: String =
    """{
      |  "openapi": "3.0.0",
      |  "info": {
      |    "title": "API with Types",
      |    "version": ""
      |  },
      |  "paths": {
      |    "/users/{id}": {
      |      "get": {
      |        "operationId": "GET_users-id",
      |        "produces": [ "application/json" ],
      |        "responses": {
      |          "200": {
      |            "description": "",
      |            "content": {
      |              "null": {
      |                "schema": { "$ref": "cat-schema.json" }
      |              }
      |            }
      |          }
      |        }
      |      }
      |    }
      |  }
      |}""".stripMargin
  private val apiStringYaml: String =
    """openapi: 3.0.0
      |info:
      |  title: API with Types
      |  version: ""
      |paths:
      |  "/users/{id}":
      |    get:
      |      operationId: GET_users-id
      |      responses:
      |        "200":
      |          description: ""
      |          content:
      |            application/json:
      |              schema:
      |                $ref: "#/components/schemas/User"
      |    parameters:
      |      - in: path
      |        name: id
      |        required: true
      |        schema:
      |          type: string
      |components:
      |  schemas:
      |    User:
      |      type: object
      |      properties:
      |        firstName:
      |          type: string
      |        lastName:
      |          type: string
      |        age:
      |          minimum: 0
      |          maximum: 99
      |          type: integer
      |      required:
      |        - firstName
      |        - lastName
      |        - age""".stripMargin
  private val apiStringYamlWithRef: String =
    """openapi: 3.0.0
      |info:
      |  title: API with Types
      |  version: ""
      |paths:
      |  "/users/{id}":
      |    get:
      |      operationId: GET_users-id
      |      responses:
      |        "200":
      |          description: ""
      |          content:
      |            application/json:
      |              schema:
      |                $ref: cat-schema.yaml""".stripMargin

  test("JSON string parsing with reference and baseUrl param") {
    for {
      unit <- Oas30.parse(
        apiStringWithRef,
        "file://shared/src/test/resources/includes/api.json").asInternal
      resolved <- Oas30.resolve(unit).asInternal
      genStr <- Oas30.generateString(resolved).asInternal
    } yield {
      val doc = unit.asInstanceOf[WebApiDocument]
      doc.location should be ("file://shared/src/test/resources/includes/api.json")
      genStr should not include ("$ref")
      genStr should include ("The cat's name")
    }
  }

  test("JSON string parsing with reference and no baseUrl param") {
    for {
      unit <- Oas30.parse(apiStringWithRef).asInternal
      resolved <- Oas30.resolve(unit).asInternal
      genStr <- Oas30.generateString(resolved).asInternal
    } yield {
      val doc = unit.asInstanceOf[WebApiDocument]
      doc.location should be ("http://a.ml/amf/default_document")
      genStr should not include ("$ref")
      genStr should not include ("The cat's name")
    }
  }

  test("JSON string parsing") {
    for {
      unit <- Oas30.parse(apiString).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("JSON file parsing") {
    for {
      unit <- Oas30.parse(validFilePath).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("JSON file generation") {
    for {
      unit <- Oas30.parse(apiString).asInternal
      _ <- Oas30.generateFile(unit, s"file://${generatedJsonFilePath}").asInternal
    } yield {
      val genStr = waitAndRead(generatedJsonFilePath)
      genStr should include ("schemas")
      genStr should include ("/users/{id}")
    }
  }

  test("JSON string generation") {
    for {
      unit <- Oas30.parse(apiString).asInternal
      genStr <- Oas30.generateString(unit).asInternal
    } yield {
      genStr should include ("schemas")
      genStr should include ("/users/{id}")
    }
  }

  test("Validation") {
    for {
      unit <- Oas30.parse(invalidFilePath).asInternal
      report <- Oas30.validate(unit).asInternal
    } yield {
      report.conforms should be (false)
      report.results should have size 1
    }
  }

  test("Resolution") {
    for {
      unit <- Oas30.parse(apiString).asInternal
      resolved <- Oas30.resolve(unit).asInternal
      genStr <- Oas30.generateString(resolved).asInternal
    } yield {
      genStr should not include ("$ref")
      genStr should not include ("schemas")
      genStr should not include ("User")
      genStr should include ("firstName")
      genStr should include ("schema")
    }
  }

  test("Resolution with preserving root definitions") {
    for {
      unit <- Oas30.parse(apiString).asInternal
      resolved <- Oas30.resolve(unit, true).asInternal
      genStr <- Oas30.generateString(resolved).asInternal
    } yield {
      genStr should not include ("$ref")
      genStr should include ("schemas")
      genStr should include ("User")
      genStr should include ("firstName")
      genStr should include ("schema")
    }
  }

  test("YAML string parsing with reference and baseUrl param") {
    for {
      unit <- Oas30.parseYaml(
        apiStringWithRef,
        "file://shared/src/test/resources/includes/api.yaml").asInternal
      resolved <- Oas30.resolve(unit).asInternal
      genStr <- Oas30.generateYamlString(resolved).asInternal
    } yield {
      val doc = unit.asInstanceOf[WebApiDocument]
      doc.location should be ("file://shared/src/test/resources/includes/api.yaml")
      genStr should not include ("$ref")
      genStr should include ("The cat's name")
    }
  }

  test("YAML string parsing with reference and no baseUrl param") {
    for {
      unit <- Oas30.parseYaml(apiStringWithRef).asInternal
      resolved <- Oas30.resolve(unit).asInternal
      genStr <- Oas30.generateYamlString(resolved).asInternal
    } yield {
      val doc = unit.asInstanceOf[WebApiDocument]
      doc.location should be ("http://a.ml/amf/default_document")
      genStr should not include ("$ref")
      genStr should not include ("The cat's name")
    }
  }

  test("YAML string parsing") {
    for {
      unit <- Oas30.parseYaml(apiStringYaml).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("YAML file parsing") {
    for {
      unit <- Oas30.parseYaml(validFilePathYaml).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("YAML file generation") {
    for {
      unit <- Oas30.parseYaml(apiStringYaml).asInternal
      _ <- Oas30.generateYamlFile(unit, s"file://${generatedYamlFilePath}").asInternal
    } yield {
      val genStr = waitAndRead(generatedYamlFilePath)
      genStr should include ("title: API with Types")
      genStr should include ("/users/{id}:")
    }
  }

  test("YAML string generation") {
    for {
      unit <- Oas30.parseYaml(apiStringYaml).asInternal
      genStr <- Oas30.generateYamlString(unit).asInternal
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
