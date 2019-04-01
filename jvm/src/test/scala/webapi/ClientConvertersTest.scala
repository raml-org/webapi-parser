package webapi

import webapi.WebApiClientConverters._

import amf.client.model.document.BaseUnit
import amf.core.model.document.{BaseUnit => InternalBaseUnit}

import scala.concurrent.Future
import org.scalatest.{AsyncFunSuite, Matchers, Assertion}
import org.scalatest.Assertions._


class ClientConvertersTest extends AsyncFunSuite with Matchers {

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

  test("Conversion WebApiBaseUnit -> BaseUnit") {
    val prom: ClientFuture[WebApiBaseUnit] = Raml10.parse(apiString)
    val converted: ClientFuture[BaseUnit] = prom
    for {
      model <- converted.asInternal
    } yield {
      model shouldBe a [InternalBaseUnit]
    }
  }

  test("Conversion BaseUnit -> WebApiBaseUnit") {
    val prom: ClientFuture[WebApiBaseUnit] = Raml10.parse(apiString)
    val converted: ClientFuture[BaseUnit] = prom
    val convertedAgain: ClientFuture[WebApiBaseUnit] = converted
    for {
      model <- convertedAgain.asInternal
    } yield {
      model shouldBe a [WebApiBaseUnit]
      model.asInstanceOf[WebApiDocument] shouldBe a [WebApiDocument]
    }
  }

  test("Conversion with WebApiBaseUnit.asClient/asInternal") {
    val prom: ClientFuture[WebApiBaseUnit] = Raml10.parse(apiString)
    val converted = prom.asInternal
    val convertedAgain = converted.asClient
    for {
      model1 <- converted
      model2 <- convertedAgain.asInternal
    } yield {
      model1 shouldBe a [WebApiBaseUnit]
      model2 shouldBe a [WebApiBaseUnit]
    }
  }
}
