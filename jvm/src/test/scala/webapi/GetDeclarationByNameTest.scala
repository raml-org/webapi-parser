package webapi

import webapi.WebApiClientConverters._

import amf.client.model.domain.{
  NodeShape, UnionShape, ArrayShape, MatrixShape
}

import scala.concurrent.Future
import scala.reflect.ClassTag
import org.scalatest.{AsyncFunSuite, Matchers, Assertion}
import org.scalatest.Assertions._


class GetDeclarationByNameTest extends AsyncFunSuite with Matchers {

  private val apiWithTypesRaml     = "file://shared/src/test/resources/raml/api-with-types.raml"
  private val library              = "file://shared/src/test/resources/raml/fragments/library.raml"
  private val exprLibrary          = "file://shared/src/test/resources/raml/fragments/library-with-expressions.raml"

  test("Get declaration from resolved RAML 1.0 Document") {
    for {
      model <- Raml10.parse(apiWithTypesRaml).asInternal
      resolved <- Raml10.resolve(model).asInternal
    } yield {
      assertDeclaration[NodeShape](resolved, "User")
    }
  }

  test("Get declaration from RAML 1.0 Document") {
    getAndAssertRamlDeclaration[NodeShape](apiWithTypesRaml, "User")
  }

  test("Get declaration from RAML 1.0 Library") {
    getAndAssertRamlDeclaration[NodeShape](library, "Admin")
  }

  test("Get declaration from RAML 1.0 Extension") {
    getAndAssertRamlDeclaration[NodeShape](
      "file://shared/src/test/resources/raml/fragments/extension.raml",
      "Admin")
  }

  test("Get declaration from RAML 1.0 Overlay") {
    getAndAssertRamlDeclaration[NodeShape](
      "file://shared/src/test/resources/raml/fragments/overlay.raml",
      "Person")
  }

  test("Get declaration from RAML 1.0 DataType") {
    getAndAssertRamlDeclaration[NodeShape](
      "file://shared/src/test/resources/raml/fragments/datatype.raml",
      "type")
  }

  test("Error when trying to get inexisting declaration from RAML 1.0") {
    val futureEx = recoverToExceptionIf[Exception] {
      for {
        model <- Raml10.parse(library).asInternal
      } yield {
        model.getDeclarationByName("FooBarBaz")
      }
    }
    futureEx map {
      ex => assert(ex.getMessage == "Declaration with name 'FooBarBaz' not found")
    }
  }

  test("Get declaration from OAS 2.0 Document") {
    for {
      model <- Oas20.parse("file://shared/src/test/resources/oas/api-with-types.json").asInternal
    } yield {
      assertDeclaration[NodeShape](model, "User")
    }
  }

  test("Get type defined as type expression: Union") {
    getAndAssertRamlDeclaration[UnionShape](exprLibrary, "CatDogUnion")
  }

  test("Get type defined as type expression: Multiple Inheritance") {
    getAndAssertRamlDeclaration[NodeShape](exprLibrary, "CatDogMultiInheritance")
  }

  test("Get type defined as type expression: Array") {
    getAndAssertRamlDeclaration[ArrayShape](exprLibrary, "CatArray")
  }

  test("Get type defined as type expression: Union Array") {
    getAndAssertRamlDeclaration[ArrayShape](exprLibrary, "CatDogUnionArray")
  }

  test("Get type defined as type expression: Array of strings") {
    getAndAssertRamlDeclaration[ArrayShape](exprLibrary, "stringArray")
  }

  test("Get type defined as type expression: Matrix of strings") {
    getAndAssertRamlDeclaration[MatrixShape](exprLibrary, "stringMatrix")
  }

  def getAndAssertRamlDeclaration[T:ClassTag] (filePath: String, declarationName: String): Future[Assertion] = {
    for {
      model <- Raml10.parse(filePath).asInternal
    } yield {
      assertDeclaration[T](model, declarationName)
    }
  }

  def assertDeclaration[T:ClassTag] (model: WebApiBaseUnit, declarationName: String): Assertion = {
    val declaration = model.getDeclarationByName(declarationName)
    declaration shouldBe a [T]
    declaration.name.value() should be (declarationName)
    assert(!(declaration.isLink))
  }
}
