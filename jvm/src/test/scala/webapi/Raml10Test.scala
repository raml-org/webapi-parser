package webapi

import webapi.WebApiClientConverters._

import amf.client.model.domain.{WebApi}

import org.scalatest.{AsyncFunSuite, Matchers, Assertion}
import org.scalatest.Assertions._


class Raml10Test extends AsyncFunSuite with Matchers with WaitingFileReader {

  private val validFilePath     = "file://shared/src/test/resources/raml/api-with-types.raml"
  private val invalidFilePath   = "file://shared/src/test/resources/raml/invalid-examples.raml"
  private val generatedFilePath = s"${System.getProperty("java.io.tmpdir")}/generated-raml10.raml"
  private val apiString: String =
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
      |/users/{id}:
      |  get:
      |    responses:
      |      200:
      |        body:
      |          application/json:
      |            type: User""".stripMargin

  private val apiStringWithRef: String =
    """#%RAML 1.0
      |title: API with Types
      |/users/{id}:
      |  get:
      |    responses:
      |      200:
      |        body:
      |          application/json:
      |            type: !include cat-schema.json""".stripMargin

  test("String parsing with reference and basePath") {
    for {
      unit <- Raml10.parse(
        apiStringWithRef,
        "file://shared/src/test/resources/includes/").asInternal
      resolved <- Raml10.resolve(unit).asInternal
      genStr <- Raml10.generateString(resolved).asInternal
    } yield {
      genStr should not include ("!include")
      genStr should include ("The cat's name")
    }
  }

  test("String parsing with reference and no basePath") {
    for {
      unit <- Raml10.parse(apiStringWithRef).asInternal
      resolved <- Raml10.resolve(unit).asInternal
      genStr <- Raml10.generateString(resolved).asInternal
    } yield {
      genStr should not include ("!include")
      genStr should include ("type: any")
    }
  }

  test("String parsing") {
    for {
      unit <- Raml10.parse(apiString).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("File parsing") {
    for {
      unit <- Raml10.parse(validFilePath).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("File generation") {
    for {
      unit <- Raml10.parse(apiString).asInternal
      _ <- Raml10.generateFile(unit, s"file://${generatedFilePath}").asInternal
    } yield {
      val genStr = waitAndRead(generatedFilePath)
      genStr should include ("#%RAML 1.0")
      genStr should include ("/users/{id}:")
    }
  }

  test("String generation") {
    for {
      unit <- Raml10.parse(apiString).asInternal
      genStr <- Raml10.generateString(unit).asInternal
    } yield {
      genStr should include ("#%RAML 1.0")
      genStr should include ("/users/{id}:")
    }
  }

  test("Validation") {
    for {
      unit <- Raml10.parse(invalidFilePath).asInternal
      report <- Raml10.validate(unit).asInternal
    } yield {
      report.conforms should be (false)
      report.results should have size 3
    }
  }

  test("Resolution") {
    for {
      unit <- Raml10.parse(apiString).asInternal
      resolved <- Raml10.resolve(unit).asInternal
      genStr <- Raml10.generateString(resolved).asInternal
    } yield {
      genStr should include ("firstName:")
      genStr should not include ("type: User")
      genStr should not include ("types:")
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
