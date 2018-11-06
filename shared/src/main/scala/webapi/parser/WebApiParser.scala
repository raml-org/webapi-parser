package webapi.parser

import amf.{Core, MessageStyle, MessageStyles, ProfileName, ProfileNames}
import amf.plugins.document.{WebApi}
import amf.plugins.document.webapi.validation.PayloadValidatorPlugin
// import amf.plugins.features.AMFValidation
import amf.client.parse.{Raml10Parser}
import amf.client.render.{Raml10Renderer, Oas20Renderer}
import amf.client.model.document.{BaseUnit}
import amf.client.validate.ValidationReport
import amf.client.convert.CoreClientConverters._

import scala.scalajs.js.annotation._

@JSExportAll
@JSExportTopLevel("WebApiParser")
object WebApiParser {
  {
    println(">>> WebApiParser init started")
    WebApi.register()
    // AMFValidation.register()
    amf.Core.registerPlugin(PayloadValidatorPlugin)
    amf.Core.init()
    println(">>> WebApiParser init ended")
  }

  def raml10Parser(): Raml10Parser = new Raml10Parser()

  def raml10Generator(): Raml10Renderer = new Raml10Renderer()

  def validateRaml10(model: BaseUnit): ClientFuture[ValidationReport] =
    Core.validate(model, ProfileNames.RAML, MessageStyles.RAML)

  def oas20Generator(): Oas20Renderer = new Oas20Renderer()
}
