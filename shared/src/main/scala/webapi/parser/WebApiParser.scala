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
  var initialized: Boolean = false

  def init(): ClientFuture[Unit] = {
    if(initialized) {
      val emptyFuture: Future[Unit] = Future {}
      emptyFuture.asClient
    } else {
      println(">>> Running WebApiParser.init")
      initialized = true
      WebApi.register()
      amf.Core.registerPlugin(PayloadValidatorPlugin)
      amf.Core.init()
    }
  }

  @JSExportAll
  @JSExportTopLevel("WebApiParser.raml10")
  object raml10 {
    def parseFile(url: String): ClientFuture[BaseUnit] = {
      new Raml10Parser().parseFileAsync(url)
    }

    def parseString(content: String): ClientFuture[BaseUnit] = {
      new Raml10Parser().parseStringAsync(content)
    }

    def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
      new Raml10Renderer().generateFile(model, url)
    }

    def generateString(model: BaseUnit): ClientFuture[String] = {
      new Raml10Renderer().generateString(model)
    }

    def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
      Core.validate(model, ProfileNames.RAML10, MessageStyles.RAML)
    }

    def resolve(model: BaseUnit): BaseUnit = {
      // TODO: Return ClientFuture[BaseUnit]
      new Raml10Resolver().resolve(model)
    }
  }

  @JSExportAll
  @JSExportTopLevel("WebApiParser.raml08")
  object raml08 {
    def parseFile(url: String): ClientFuture[BaseUnit] = {
      new Raml08Parser().parseFileAsync(url)
    }

    def parseString(content: String): ClientFuture[BaseUnit] = {
      new Raml08Parser().parseStringAsync(content)
    }

    def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
      new Raml08Renderer().generateFile(model, url)
    }

    def generateString(model: BaseUnit): ClientFuture[String] = {
      new Raml08Renderer().generateString(model)
    }

    def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
      Core.validate(model, ProfileNames.RAML08, MessageStyles.RAML)
    }

    def resolve(model: BaseUnit): BaseUnit = {
      // TODO: Return ClientFuture[BaseUnit]
      new Raml08Resolver().resolve(model)
    }
  }

  @JSExportAll
  @JSExportTopLevel("WebApiParser.oas20")
  object oas20 {
    def parseFile(url: String): ClientFuture[BaseUnit] = {
      new Oas20Parser().parseFileAsync(url)
    }

    def parseString(content: String): ClientFuture[BaseUnit] = {
      new Oas20Parser().parseStringAsync(content)
    }

    def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
      new Oas20Renderer().generateFile(model, url)
    }

    def generateString(model: BaseUnit): ClientFuture[String] = {
      new Oas20Renderer().generateString(model)
    }

    def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
      Core.validate(model, ProfileNames.OAS20, MessageStyles.OAS)
    }

    def resolve(model: BaseUnit): BaseUnit = {
      // TODO: Return ClientFuture[BaseUnit]
      new Oas20Resolver().resolve(model)
    }

    // Specific to oas20 object
    def parseYamlFile(url: String): ClientFuture[BaseUnit] = {
      new Oas20YamlParser().parseFileAsync(url)
    }

    def parseYamlString(content: String): ClientFuture[BaseUnit] = {
      new Oas20YamlParser().parseStringAsync(content)
    }
  }

  @JSExportAll
  @JSExportTopLevel("WebApiParser.amfGraph")
  object amfgraph {
    def parseFile(url: String): ClientFuture[BaseUnit] = {
      new AmfGraphParser().parseFileAsync(url)
    }

    def parseString(content: String): ClientFuture[BaseUnit] = {
      new AmfGraphParser().parseStringAsync(content)
    }

    def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
      new AmfGraphRenderer().generateFile(model, url)
    }

    def generateString(model: BaseUnit): ClientFuture[String] = {
      new AmfGraphRenderer().generateString(model)
    }

    def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
      Core.validate(model, ProfileNames.AMF, MessageStyles.AMF)
    }

    def resolve(model: BaseUnit): BaseUnit = {
      // TODO: Return ClientFuture[BaseUnit]
      new AmfGraphResolver().resolve(model)
    }
  }
}
