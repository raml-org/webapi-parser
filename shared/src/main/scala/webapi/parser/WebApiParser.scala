package webapi.parser

import amf.{Core, MessageStyles, ProfileNames, ProfileName}
import amf.plugins.document.{WebApi}
import amf.plugins.document.webapi.validation.PayloadValidatorPlugin
import amf.client.parse._
import amf.client.render._
import amf.client.resolve._
import amf.client.model.document.{BaseUnit}
import amf.client.validate.ValidationReport
import amf.client.convert.CoreClientConverters._

import scala.concurrent._
import scala.scalajs.js.annotation._
import ExecutionContext.Implicits.global

@JSExportAll
@JSExportTopLevel("WebApiParser")
object WebApiParser {

  // Init
  def init(): ClientFuture[Unit] = {
    WebApi.register()
    amf.Core.registerPlugin(PayloadValidatorPlugin)
    amf.Core.init()
  }


  // RAML 1.0
  def raml10Parser(): Raml10Parser = new Raml10Parser()

  def raml10Generator(): Raml10Renderer = new Raml10Renderer()

  def validateRaml10(model: BaseUnit): ClientFuture[ValidationReport] =
    Core.validate(model, ProfileNames.RAML10, MessageStyles.RAML)

  def resolveRaml10(model: BaseUnit): BaseUnit = new Raml10Resolver().resolve(model)


  // RAML 0.8
  def raml08Parser(): Raml08Parser = new Raml08Parser()

  def raml08Generator(): Raml08Renderer = new Raml08Renderer()

  def validateRaml08(model: BaseUnit): ClientFuture[ValidationReport] =
    Core.validate(model, ProfileNames.RAML08, MessageStyles.RAML)

  def resolveRaml08(model: BaseUnit): BaseUnit = new Raml08Resolver().resolve(model)


  // OAS 2
  def oas20Parser(): Oas20Parser = new Oas20Parser()

  def oas20YamlParser(): Oas20YamlParser = new Oas20YamlParser()

  def oas20Generator(): Oas20Renderer = new Oas20Renderer()

  def validateOas20(model: BaseUnit): ClientFuture[ValidationReport] =
    Core.validate(model, ProfileNames.OAS20, MessageStyles.OAS)

  def resolveOas20(model: BaseUnit): BaseUnit = new Oas20Resolver().resolve(model)


  // Amf Graph
  def amfGraphParser(): AmfGraphParser = new AmfGraphParser()

  def amfGraphGenerator(): AmfGraphRenderer = new AmfGraphRenderer()

  def validateAmfGraph(model: BaseUnit): ClientFuture[ValidationReport] =
    Core.validate(model, ProfileNames.AMF, MessageStyles.AMF)

  def resolveAmfGraph(model: BaseUnit): BaseUnit = new AmfGraphResolver().resolve(model)


  // Plain JSON and YAML
  def jsonPayloadParser(): JsonPayloadParser = new JsonPayloadParser()

  def yamlPayloadParser(): YamlPayloadParser = new YamlPayloadParser()
}
