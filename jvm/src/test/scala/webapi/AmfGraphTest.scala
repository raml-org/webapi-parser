package webapi

import webapi.WebApiClientConverters._

import amf.client.model.domain.{WebApi}

import org.scalatest.{AsyncFunSuite, Matchers, Assertion}
import org.scalatest.Assertions._

import scala.io.Source


class AmfGraphTest extends AsyncFunSuite with Matchers with WaitingFileReader {

  private val validFilePath     = "shared/src/test/resources/amf-graph/api-with-types.json"
  private val invalidFilePath   = "file://shared/src/test/resources/amf-graph/api-with-types-invalid.json"
  private val generatedFilePath = s"${System.getProperty("java.io.tmpdir")}/generated-amfgraph.json"
  private val apiString: String = Source.fromFile(validFilePath).getLines.mkString("\n")

  test("String parsing") {
    for {
      unit <- AmfGraph.parse(apiString).asInternal
    } yield {
      assertApiParsed(unit)
      val doc = unit.asInstanceOf[WebApiDocument]
      doc.location should be ("http://a.ml/amf/default_document")
    }
  }

  test("String parsing with location param") {
    for {
      unit <- AmfGraph.parse(apiString, "file://somewhere/something.jsonld").asInternal
    } yield {
      val doc = unit.asInstanceOf[WebApiDocument]
      doc.location should be ("file://somewhere/something.jsonld")
    }
  }

  test("File parsing") {
    for {
      unit <- AmfGraph.parse(s"file://${validFilePath}").asInternal
    } yield {
      assertApiParsed(unit)
    }
  }

  test("File generation") {
    for {
      unit <- AmfGraph.parse(apiString).asInternal
      _ <- AmfGraph.generateFile(unit, s"file://${generatedFilePath}").asInternal
    } yield {
      val genStr = waitAndRead(generatedFilePath)
      genStr should include ("document#Document")
      genStr should include ("/users/{id}")
    }
  }

  test("String generation") {
    for {
      unit <- AmfGraph.parse(apiString).asInternal
      genStr <- AmfGraph.generateString(unit).asInternal
    } yield {
      genStr should include ("document#Document")
      genStr should include ("/users/{id}")
    }
  }

  test("Validation") {
    for {
      unit <- AmfGraph.parse(invalidFilePath).asInternal
      report <- AmfGraph.validate(unit).asInternal
    } yield {
      report.conforms should be (false)
      report.results should have size 1
    }
  }

  test("Resolution") {
    for {
      unit <- AmfGraph.parse(apiString).asInternal
      resolved <- AmfGraph.resolve(unit).asInternal
      genStr <- AmfGraph.generateString(resolved).asInternal
    } yield {
      genStr should not include ("\"@value\":\"User\"")
      genStr should include ("http#payload")
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
