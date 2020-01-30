package webapi

import webapi.WebApiClientConverters._

import amf.client.model.domain.{
  NodeShape, UnionShape, ArrayShape, AnyShape,
  NilShape, FileShape, ScalarShape, SchemaShape
}

import scala.concurrent.Future
import scala.reflect.ClassTag
import org.scalatest.{AsyncFunSuite, Matchers, Assertion}
import org.scalatest.Assertions._


class GetDeclarationByNameTest extends AsyncFunSuite with Matchers {

  private val apiWithTypesRaml     = "file://shared/src/test/resources/raml/api-with-types.raml"
  private val library              = "file://shared/src/test/resources/raml/fragments/library.raml"
  private val complexLib          = "file://shared/src/test/resources/raml/complex-library.raml"

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
      model <- Oas20.parse("file://shared/src/test/resources/oas20/api-with-types.json").asInternal
    } yield {
      assertDeclaration[NodeShape](model, "User")
    }
  }

  test("Get type defined as type expression: Union") {
    getAndAssertRamlDeclaration[UnionShape](complexLib, "CatDogUnion")
  }

  test("Get type defined as type expression: Multiple Inheritance") {
    getAndAssertRamlDeclaration[NodeShape](complexLib, "CatDogMultiInheritance")
  }

  test("Get type defined as type expression: Array") {
    getAndAssertRamlDeclaration[ArrayShape](complexLib, "CatArray")
  }

  test("Get type defined as type expression: Union Array") {
    getAndAssertRamlDeclaration[ArrayShape](complexLib, "CatDogUnionArray")
  }

  test("Get type defined as type expression: Array of strings") {
    getAndAssertRamlDeclaration[ArrayShape](complexLib, "stringArray")
  }

  test("Get type defined as type expression: Matrix of strings") {
    getAndAssertRamlDeclaration[ArrayShape](complexLib, "stringMatrix")
  }

  test("Get type that inherits type: any") {
    getAndAssertRamlDeclaration[AnyShape](complexLib, "anyCat")
  }

  test("Get type that inherits type: string") {
    getAndAssertRamlDeclaration[ScalarShape](complexLib, "CatName")
  }

  test("Get type that inherits type: number") {
    getAndAssertRamlDeclaration[ScalarShape](complexLib, "CatAgeNum")
  }

  test("Get type that inherits type: integer") {
    getAndAssertRamlDeclaration[ScalarShape](complexLib, "CatAgeInt")
  }

  test("Get type that inherits type: boolean") {
    getAndAssertRamlDeclaration[ScalarShape](complexLib, "CatIsCool")
  }

  test("Get type that inherits type: date-only") {
    getAndAssertRamlDeclaration[ScalarShape](complexLib, "CatBirthDateOnly")
  }

  test("Get type that inherits type: time-only") {
    getAndAssertRamlDeclaration[ScalarShape](complexLib, "CatBirthTimeOnly")
  }

  test("Get type that inherits type: datetime-only") {
    getAndAssertRamlDeclaration[ScalarShape](complexLib, "CatBirthDatetimeOnly")
  }

  test("Get type that inherits type: datetime") {
    getAndAssertRamlDeclaration[ScalarShape](complexLib, "CatBirthDatetime")
  }

  test("Get type that inherits type: file") {
    getAndAssertRamlDeclaration[FileShape](complexLib, "CatPhoto")
  }

  test("Get type that inherits type: nil") {
    getAndAssertRamlDeclaration[NilShape](complexLib, "CatosaurusNil")
  }

  test("Get type that includes json schema") {
    getAndAssertRamlDeclaration[NodeShape](complexLib, "CatInJson")
  }

  test("Get type that includes json schema (using deprecated 'schema:')") {
    getAndAssertRamlDeclaration[NodeShape](complexLib, "CatInJsonSchema")
  }

  test("Get type that includes xml schema") {
    getAndAssertRamlDeclaration[SchemaShape](complexLib, "CatInXml")
  }

  test("Get type that includes xml schema (using deprecated 'schema:')") {
    getAndAssertRamlDeclaration[SchemaShape](complexLib, "CatInXmlSchema")
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
