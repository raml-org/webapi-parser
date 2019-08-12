package webapi

import webapi.WebApiClientConverters._

import amf.client.model.domain.{WebApi}

import org.scalatest.{AsyncFunSuite, Matchers, Assertion}
import org.scalatest.Assertions._


class Raml08Test extends AsyncFunSuite with Matchers with WaitingFileReader {

  private val validFilePath     = "file://shared/src/test/resources/raml/magic-api.raml"
  private val invalidFilePath   = "file://shared/src/test/resources/raml/invalid-examples-08.raml"
  private val generatedFilePath = s"${System.getProperty("java.io.tmpdir")}/generated-raml08.raml"
  private val apiString: String =
    """#%RAML 0.8
      |title: Magic API
      |version: v3
      |baseUri: https://magic.api.com
      |/items:
      |  get:
      |    description: Get a list of magic items
      |    queryParameters:
      |      page: integer
      |      per_page: integer""".stripMargin

  private val apiStringWithRef: String =
    """#%RAML 0.8
      |title: API with Types
      |/users/{id}:
      |  get:
      |    responses:
      |      200:
      |        body:
      |          application/json:
      |            schema: !include cat-schema.json""".stripMargin

  test("String parsing with reference and basePath") {
    for {
      unit <- Raml08.parse(
        apiStringWithRef,
        "file://shared/src/test/resources/includes/").asInternal
      resolved <- Raml08.resolve(unit).asInternal
      genStr <- Raml08.generateString(resolved).asInternal
    } yield {
      genStr should not include ("!include")
      genStr should include ("The cat's name")
    }
  }

  test("String parsing with reference and no basePath") {
    for {
      unit <- Raml08.parse(apiStringWithRef).asInternal
      resolved <- Raml08.resolve(unit).asInternal
      genStr <- Raml08.generateString(resolved).asInternal
    } yield {
      genStr should not include ("!include")
      genStr should not include ("The cat's name")
    }
  }

  test("String parsing") {
    for {
      unit <- Raml08.parse(apiString).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("File parsing") {
    for {
      unit <- Raml08.parse(validFilePath).asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("File generation") {
    for {
      unit <- Raml08.parse(apiString).asInternal
      _ <- Raml08.generateFile(unit, s"file://${generatedFilePath}").asInternal
    } yield {
      val genStr = waitAndRead(generatedFilePath)
      genStr should include ("#%RAML 0.8")
      genStr should include ("/items:")
    }
  }

  test("String generation") {
    for {
      unit <- Raml08.parse(apiString).asInternal
      genStr <- Raml08.generateString(unit).asInternal
    } yield {
      genStr should include ("#%RAML 0.8")
      genStr should include ("/items:")
    }
  }

  test("Validation") {
    for {
      unit <- Raml08.parse(invalidFilePath).asInternal
      report <- Raml08.validate(unit).asInternal
    } yield {
      report.conforms should be (false)
      report.results should have size 1
    }
  }

  test("Resolution") {
    for {
      unit <- Raml08.parse(apiString).asInternal
      resolved <- Raml08.resolve(unit).asInternal
      genStr <- Raml08.generateString(resolved).asInternal
    } yield {
      genStr should include ("page:")
      genStr should not include ("page: integer")
      genStr should not include ("per_page: integer")
    }
  }

  def assertApiParsed (unit: WebApiBaseUnit): Assertion = {
    val doc = unit.asInstanceOf[WebApiDocument]
    val api = doc.encodes.asInstanceOf[WebApi]
    api.endPoints should have size 1
    api.name.value() should be ("Magic API")
  }
}
