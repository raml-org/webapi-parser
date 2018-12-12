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

  private val strInp: String =
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

  test("Test RAML 1.0 string parsing") {
    for {
      unit <- Raml10.parse(strInp).asInternal
    } yield {
      val bu: BaseUnit = unit
      val doc = bu.asInstanceOf[Document]
      val api = doc.encodes.asInstanceOf[WebApi]
      doc.declares should have size 1
      api.endPoints should have size 1
      api.name.value() should be("API with Types")
    }
  }
}
