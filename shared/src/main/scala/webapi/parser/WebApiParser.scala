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

  def chainAfterInit[T](func: () => Future[T]): Future[T] = {
    val future = for {
      _ <- init().asInternal
      result <- func()
    } yield (result)
    future
  }

  @JSExportAll
  @JSExportTopLevel("WebApiParser.raml10")
  object raml10 {
    def parseFile(url: String): ClientFuture[BaseUnit] = {
      chainAfterInit(() => {
        new Raml10Parser().parseFileAsync(url).asInternal
      }).asClient
    }

    def parseString(content: String): ClientFuture[BaseUnit] = {
      chainAfterInit(() => {
        new Raml10Parser().parseStringAsync(content).asInternal
      }).asClient
    }

    def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
      chainAfterInit(() => {
        new Raml10Renderer().generateFile(model, url).asInternal
      }).asClient
    }

    def generateString(model: BaseUnit): ClientFuture[String] = {
      chainAfterInit(() => {
        new Raml10Renderer().generateString(model).asInternal
      }).asClient
    }

    def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
      chainAfterInit(() => {
        Core.validate(model, ProfileNames.RAML10, MessageStyles.RAML).asInternal
      }).asClient
    }

    // def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    //   chainAfterInit(() => {
    //     Future { new Raml10Resolver().resolve(model) }
    //   }).asClient
    // }
  }

  @JSExportAll
  @JSExportTopLevel("WebApiParser.raml08")
  object raml08 {
    def parseFile(url: String): ClientFuture[BaseUnit] = {
      chainAfterInit(() => {
        new Raml08Parser().parseFileAsync(url).asInternal
      }).asClient
    }

    def parseString(content: String): ClientFuture[BaseUnit] = {
      chainAfterInit(() => {
        new Raml08Parser().parseStringAsync(content).asInternal
      }).asClient
    }

    def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
      chainAfterInit(() => {
        new Raml08Renderer().generateFile(model, url).asInternal
      }).asClient
    }

    def generateString(model: BaseUnit): ClientFuture[String] = {
      chainAfterInit(() => {
        new Raml08Renderer().generateString(model).asInternal
      }).asClient
    }

    def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
      chainAfterInit(() => {
        Core.validate(model, ProfileNames.RAML08, MessageStyles.RAML).asInternal
      }).asClient
    }

    // def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    //   chainAfterInit(() => {
    //     Future { new Raml08Resolver().resolve(model) }
    //   }).asClient
    // }
  }

  @JSExportAll
  @JSExportTopLevel("WebApiParser.oas20")
  object oas20 {
    def parseFile(url: String): ClientFuture[BaseUnit] = {
      chainAfterInit(() => {
        new Oas20Parser().parseFileAsync(url).asInternal
      }).asClient
    }

    def parseString(content: String): ClientFuture[BaseUnit] = {
      chainAfterInit(() => {
        new Oas20Parser().parseStringAsync(content).asInternal
      }).asClient
    }

    def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
      chainAfterInit(() => {
        new Oas20Renderer().generateFile(model, url).asInternal
      }).asClient
    }

    def generateString(model: BaseUnit): ClientFuture[String] = {
      chainAfterInit(() => {
        new Oas20Renderer().generateString(model).asInternal
      }).asClient
    }

    def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
      chainAfterInit(() => {
        Core.validate(model, ProfileNames.OAS20, MessageStyles.OAS).asInternal
      }).asClient
    }

    // def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    //   chainAfterInit(() => {
    //     Future { new Oas20Resolver().resolve(model) }
    //   }).asClient
    // }

    // Specific to oas20 object
    def parseYamlFile(url: String): ClientFuture[BaseUnit] = {
      chainAfterInit(() => {
        new Oas20YamlParser().parseFileAsync(url).asInternal
      }).asClient
    }

    def parseYamlString(content: String): ClientFuture[BaseUnit] = {
      chainAfterInit(() => {
        new Oas20YamlParser().parseStringAsync(content).asInternal
      }).asClient
    }
  }

  @JSExportAll
  @JSExportTopLevel("WebApiParser.amfGraph")
  object amfgraph {
    def parseFile(url: String): ClientFuture[BaseUnit] = {
      chainAfterInit(() => {
        new AmfGraphParser().parseFileAsync(url).asInternal
      }).asClient
    }

    def parseString(content: String): ClientFuture[BaseUnit] = {
      chainAfterInit(() => {
        new AmfGraphParser().parseStringAsync(content).asInternal
      }).asClient
    }

    def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
      chainAfterInit(() => {
        new AmfGraphRenderer().generateFile(model, url).asInternal
      }).asClient
    }

    def generateString(model: BaseUnit): ClientFuture[String] = {
      chainAfterInit(() => {
        new AmfGraphRenderer().generateString(model).asInternal
      }).asClient
    }

    def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
      chainAfterInit(() => {
        Core.validate(model, ProfileNames.AMF, MessageStyles.AMF).asInternal
      }).asClient
    }

    // def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    //   chainAfterInit(() => {
    //     Future { new AmfGraphResolver().resolve(model) }
    //   }).asClient
    // }
  }
}
