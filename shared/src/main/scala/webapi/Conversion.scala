package webapi

import amf.client.parse._
import amf.client.render._
import amf.client.model.document.{BaseUnit, Document, DataType}
import amf.client.model.document.{Module => Library}
import amf.client.model.domain.{NodeShape => DomainNodeShape}
import amf.plugins.domain.shapes.models.NodeShape
import amf.client.convert.CoreClientConverters._

import scala.concurrent._
import scala.scalajs.js.annotation._
import ExecutionContext.Implicits.global
import collection.mutable.Map


/** Provides RAML DT <> JSON Schema conversion utils */
@JSExportTopLevel("Conversion")
object Conversion {

  /** Converts type from RAML 1.0 input to JSON Schema.
    * Supported input docs: RAML API spec, RAML Library, Raml DataType.
    *
    * @param ramlInp RAML 1.0 file url/path or content string.
    * @param typeName Name of type to be converted.
    * @return Converted type as JSON Schema string.
    */
  @JSExport
  def toJsonSchema(ramlInp: String, typeName: String): ClientFuture[String] = {
    (for {
      model <- Raml10.parse(ramlInp).asInternal
    } yield {
      val typesJsonMap = composeTypesJsonMap(model, typeName)
      pickMatchingJson(typeName, typesJsonMap)
    }).asClient
  }

  // Composes map of {type1Name -> type1Json, ...}
  private def composeTypesJsonMap(model: BaseUnit, typeName: String): Map[String, String] = {
    model match {
      case lib: Library => composeRamlTypesMapLibrary(lib, typeName)
      case dt: DataType => composeRamlTypesMapDataType(dt, typeName)
      case doc: Document => composeRamlTypesMapDocument(doc, typeName)
    }
  }

  // Composes map of typeName->typeJson from RAML API
  private def composeRamlTypesMapDocument(model: Document, typeName: String): Map[String, String] = {
    var tm = Map[String, String]()
    model.declares.asInternal foreach {
      domainEl => {
        var shape = domainEl.asInstanceOf[NodeShape]
        tm += (shape.name.value() -> shape.toJsonSchema)
      }
    }
    tm
  }

  // Composes map of typeName->typeJson from RAML Library
  private def composeRamlTypesMapLibrary(model: Library, typeName: String): Map[String, String] = {
    var tm = Map[String, String]()
    model.declares.asInternal foreach {
      domainEl => {
        var shape = domainEl.asInstanceOf[NodeShape]
        tm += (shape.name.value() -> shape.toJsonSchema)
      }
    }
    tm
  }

  // Composes map of typeName->typeJson from RAML DataType
  private def composeRamlTypesMapDataType(model: DataType, typeName: String): Map[String, String] = {
    var shape = model.encodes.asInstanceOf[DomainNodeShape]
    Map[String, String](typeName -> shape.toJsonSchema)
  }

  // Picks JSON string for a type with matching name
  private def pickMatchingJson(typeName: String, typesJsonMap: Map[String, String]): String = {
    typesJsonMap.get(typeName) match {
      case Some(typeJson) => typeJson
      case None => throw new Exception(s"Type with name '$typeName' does not exist")
    }
  }
}
