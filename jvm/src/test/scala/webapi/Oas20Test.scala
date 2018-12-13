package webapi

import amf.client.model.document.{Document, BaseUnit}
import amf.client.model.domain.{WebApi}
import amf.client.convert.CoreClientConverters._

import org.scalatest.{AsyncFunSuite, Matchers, Assertion}
import org.scalatest.Assertions._


class Oas20Test extends AsyncFunSuite with Matchers with WaitingFileReader {

  private val validFilePath     = "file://shared/src/test/resources/oas/api-with-types.json"
  private val validFilePathYaml = "file://shared/src/test/resources/oas/api-with-types.yaml"
  private val invalidFilePath   = "file://shared/src/test/resources/oas/api-with-types-invalid.json"
  private val generatedFilePath = s"${System.getProperty("java.io.tmpdir")}/generated-oas20.json"
  private val apiString: String =
    """{
      |"openapi": "2.0",
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
  private val apiStringYaml: String =
    """openapi: '2.0'
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

  test("String parsing") {
    for {
      unit <- Oas20.parse(apiString).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("File parsing") {
    for {
      unit <- Oas20.parse(validFilePath).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("File generation") {
    for {
      unit <- Oas20.parse(apiString).asInternal
      _ <- Oas20.generateFile(unit, s"file://${generatedFilePath}").asInternal
    } yield {
      val genStr = waitAndRead(generatedFilePath)
      genStr should include ("definitions")
      genStr should include ("/users/{id}")
    }
  }

  test("String generation") {
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
      genStr should include ("firstName")
      genStr should include ("schema")
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

  def assertApiParsed (unit: BaseUnit): Assertion = {
    val doc = unit.asInstanceOf[Document]
    val api = doc.encodes.asInstanceOf[WebApi]
    doc.declares should have size 1
    api.endPoints should have size 1
    api.name.value() should be ("API with Types")
  }
}
