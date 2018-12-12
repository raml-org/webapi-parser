package webapi

import amf.client.model.document.{Document, BaseUnit}
import amf.client.model.domain.{WebApi}
import amf.client.convert.CoreClientConverters._

import org.scalatest.AsyncFunSuite
import org.scalatest.Matchers
import org.scalatest.Assertions._

import scala.concurrent._
import scala.concurrent.{ExecutionContext, Future}


class Raml10Test extends AsyncFunSuite with Matchers {

  override implicit val executionContext: ExecutionContext = ExecutionContext.Implicits.global

  private val raml10Str: String =
    """#%RAML 1.0
      |title: API with Types
      |version: 1.2.3
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

  test("Test RAML 1.0 string parsing") {
    for {
      unit <- Raml10.parse(raml10Str).asInternal
    } yield {
      val bu: BaseUnit = unit
      val doc = bu.asInstanceOf[Document]
      val api = doc.encodes.asInstanceOf[WebApi]
      val endPoints = api.endPoints
      assert(endPoints.size == 1)
      assert(doc.declares.size == 1)
      assert(api.version.value == "1.2.3")
    }
  }
}
