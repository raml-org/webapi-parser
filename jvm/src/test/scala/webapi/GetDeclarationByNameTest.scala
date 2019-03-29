package webapi

import webapi.WebApiClientConverters._

import amf.client.model.domain.NodeShape

import scala.concurrent.Future
import org.scalatest.{AsyncFunSuite, Matchers, Assertion}
import org.scalatest.Assertions._


class GetDeclarationByNameTest extends AsyncFunSuite with Matchers {

  test("Get declaration from resolved RAML 1.0 Document") {
    for {
      model <- Raml10.parse("file://shared/src/test/resources/raml/api-with-types.raml").asInternal
      resolved <- Raml10.resolve(model)
    } yield {
      assertDeclaration(resolved, "User")
    }
  }

  test("Get declaration from RAML 1.0 Document") {
    getAndAssertRamlDeclaration(
      "file://shared/src/test/resources/raml/api-with-types.raml",
      "User")
  }

  test("Get declaration from RAML 1.0 Library") {
    getAndAssertRamlDeclaration(
      "file://shared/src/test/resources/raml/fragments/library.raml",
      "Person")
  }

  test("Get declaration from RAML 1.0 Extension") {
    getAndAssertRamlDeclaration(
      "file://shared/src/test/resources/raml/fragments/extension.raml",
      "Person")
  }

  test("Get declaration from RAML 1.0 Overlay") {
    getAndAssertRamlDeclaration(
      "file://shared/src/test/resources/raml/fragments/overlay.raml",
      "Person")
  }

  test("Get declaration from RAML 1.0 DataType") {
    getAndAssertRamlDeclaration(
      "file://shared/src/test/resources/raml/fragments/datatype.raml",
      "type")
  }

  test("Error when trying to get inexisting declaration from RAML 1.0") {
    val futureEx = recoverToExceptionIf[Exception] {
      for {
        model <- Raml10.parse("file://shared/src/test/resources/raml/fragments/library.raml").asInternal
      } yield {
        model.getDeclarationByName("FooBarBaz")
      }
    }
    futureEx map {
      ex => assert(ex.getMessage == "Declaration with name 'FooBarBaz' not found")
    }
  }

  test("Get declaration from RAML 1.0 Document") {
    for {
      model <- Oas20.parse("file://shared/src/test/resources/oas/api-with-types.json").asInternal
    } yield {
      assertDeclaration(model, "User")
    }
  }

  def getAndAssertRamlDeclaration (filePath: String, declarationName: String): Future[Assertion] = {
    for {
      model <- Raml10.parse(filePath).asInternal
    } yield {
      assertDeclaration(model, declarationName)
    }
  }

  def assertDeclaration (model: WebApiBaseUnit, declarationName: String): Assertion = {
    val declaration = model.getDeclarationByName(declarationName)
    declaration shouldBe a [NodeShape]
    declaration.name.value() should be (declarationName)
  }
}
