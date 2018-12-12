package webapi

import amf.client.model.document.{Document, BaseUnit}
import amf.client.model.domain.{WebApi, EndPoint, Response, Operation, Payload}
import amf.client.convert.CoreClientConverters._

import org.scalatest.AsyncFunSuite
import org.scalatest.Matchers
import org.scalatest.Assertions._

import scala.concurrent.{ExecutionContext, Future}
import scala.io.Source


class Raml10Test extends AsyncFunSuite with Matchers {

  override implicit val executionContext: ExecutionContext = ExecutionContext.Implicits.global

  private val validFilePath     = "file://shared/src/test/resources/raml/api-with-types.raml"
  private val invalidFilePath   = "file://shared/src/test/resources/raml/invalid-examples.raml"
  private val generatedFilePath = s"${System.getProperty("java.io.tmpdir")}/generated.raml"
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

  test("String parsing") {
    for {
      unit <- Raml10.parse(apiString).asInternal
    } yield {
      val bu: BaseUnit = unit
      val doc = bu.asInstanceOf[Document]
      val api = doc.encodes.asInstanceOf[WebApi]
      doc.declares should have size 1
      api.endPoints should have size 1
      api.name.value() should be ("API with Types")
    }
  }

  test("File parsing") {
    for {
      unit <- Raml10.parse(validFilePath).asInternal
    } yield {
      val bu: BaseUnit = unit
      val doc = bu.asInstanceOf[Document]
      val api = doc.encodes.asInstanceOf[WebApi]
      doc.declares should have size 1
      api.endPoints should have size 1
      api.name.value() should be ("API with Types")
    }
  }

  test("File generation") {
    for {
      unit <- Raml10.parse(apiString).asInternal
      _ <- Raml10.generateFile(unit, s"file://${generatedFilePath}").asInternal
    } yield {
      val gen = Source.fromFile(generatedFilePath).getLines.toList
      gen should contain ("#%RAML 1.0")
      gen should contain ("/users/{id}:")
    }
  }

  test("String generation") {
    for {
      unit <- Raml10.parse(apiString).asInternal
      genStr <- Raml10.generateString(unit).asInternal
    } yield {
      val gen = genStr.split("\n").map(_.trim)
      gen should contain ("#%RAML 1.0")
      gen should contain ("/users/{id}:")
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
      val gen = genStr.split("\n").map(_.trim)
      gen should contain ("firstName:")
      gen should not contain ("type: User")
      gen should not contain ("types:")
    }
  }
}
