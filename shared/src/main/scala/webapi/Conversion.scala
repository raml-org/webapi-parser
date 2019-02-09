package webapi

import amf.{Core, MessageStyles, ProfileNames}
import amf.plugins.document.{WebApi}
import amf.plugins.document.webapi.validation.PayloadValidatorPlugin
import amf.plugins.features.AMFValidation
import amf.client.parse._
import amf.client.render._
import amf.client.resolve._
import amf.client.model.document.{BaseUnit}
import amf.core.model.document.{BaseUnit => InternalBaseUnit}
import amf.client.validate.ValidationReport
import amf.client.convert.CoreClientConverters._

import scala.concurrent._
import scala.scalajs.js.annotation._
import ExecutionContext.Implicits.global


@JSExportTopLevel("Conversion")
object Conversion {

  @JSExport
  def toJsonSchema(ramlInp: String, typeNames: String*): ClientFuture[String] = {
    (for {
      model <- Raml10.parse(ramlInp).asInternal
    } yield {
      // TODO:
      // 0. typesMap = composeRamlTypesMap(model)
      // 1. For each name from typeNames (composes convertedTypes):
      //    * typeNode = find name in typesMap
      //    * convertSingleRamlType(name, typeNode)
      // 2. composeJsonSchema(convertedTypes)

    }).asClient
  }

  def composeRamlTypesMap(model: BaseUnit): TODO_TYPES_MAP = {
    // TODO:
    // 1. Handle API doc
    // 2. Handle Library
    // 3. Handle DataType
  }

  def convertSingleRamlType(typeName: String, typeNode: TODO_TYPE_2): String = {
    // TODO: Convert single RAML type to json schema
  }

  def composeJsonSchema(convertedTypes: String[]): String = {
    // TODO: Compose single json schema document from array of converted types
  }

  // @JSExport
  // def toRamlDt(jsonSchemaInp: String, typeNames: String*): ClientFuture[String] = {}
}
