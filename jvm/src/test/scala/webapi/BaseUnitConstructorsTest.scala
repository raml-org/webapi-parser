package webapi

import webapi.WebApiClientConverters._

import org.scalatest.{FunSuite, Matchers, Assertion}
import org.scalatest.Assertions._


class BaseUnitConstructorsTest extends FunSuite with Matchers {

  test("WebApiDocument empty constructor") {
    val model = new WebApiDocument()
    model shouldBe a [WebApiDocument]
  }

  test("WebApiModule empty constructor") {
    val model = new WebApiModule()
    model shouldBe a [WebApiModule]
  }

  test("WebApiExternalFragment empty constructor") {
    val model = new WebApiExternalFragment()
    model shouldBe a [WebApiExternalFragment]
  }

  test("WebApiExtension empty constructor") {
    val model = new WebApiExtension()
    model shouldBe a [WebApiExtension]
  }

  test("WebApiOverlay empty constructor") {
    val model = new WebApiOverlay()
    model shouldBe a [WebApiOverlay]
  }

  test("WebApiDocumentationItem empty constructor") {
    val model = new WebApiDocumentationItem()
    model shouldBe a [WebApiDocumentationItem]
  }

  test("WebApiNamedExample empty constructor") {
    val model = new WebApiNamedExample()
    model shouldBe a [WebApiNamedExample]
  }

  test("WebApiResourceTypeFragment empty constructor") {
    val model = new WebApiResourceTypeFragment()
    model shouldBe a [WebApiResourceTypeFragment]
  }

  test("WebApiTraitFragment empty constructor") {
    val model = new WebApiTraitFragment()
    model shouldBe a [WebApiTraitFragment]
  }

  test("WebApiAnnotationTypeDeclaration empty constructor") {
    val model = new WebApiAnnotationTypeDeclaration()
    model shouldBe a [WebApiAnnotationTypeDeclaration]
  }

  test("WebApiSecuritySchemeFragment empty constructor") {
    val model = new WebApiSecuritySchemeFragment()
    model shouldBe a [WebApiSecuritySchemeFragment]
  }

  test("WebApiVocabulary empty constructor") {
    val model = new WebApiVocabulary()
    model shouldBe a [WebApiVocabulary]
  }
}
