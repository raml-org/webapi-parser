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
      toJsonSchemaInternal(model)
    }).asClient
  }

  def toJsonSchemaInternal(model: BaseUnit, typeNames: String*): String = {
    val schema = "TODO"
    schema
  }

  // @JSExport
  // def toRamlDt(jsonSchemaInp: String, typeNames: String*): ClientFuture[String] = {}
}
