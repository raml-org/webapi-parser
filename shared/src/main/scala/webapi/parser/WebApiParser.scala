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
      val future = for {
        _ <- init().asInternal
        result <- new Raml10Parser().parseFileAsync(url).asInternal
      } yield (result)
      future.asClient
    }

    def parseString(content: String): ClientFuture[BaseUnit] = {
      val future = for {
        _ <- init().asInternal
        result <- new Raml10Parser().parseStringAsync(content).asInternal
      } yield (result)
      future.asClient
    }

    def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
      val future = for {
        _ <- init().asInternal
        result <- new Raml10Renderer().generateFile(model, url).asInternal
      } yield (result)
      future.asClient
    }

    def generateString(model: BaseUnit): ClientFuture[String] = {
      val future = for {
        _ <- init().asInternal
        result <- new Raml10Renderer().generateString(model).asInternal
      } yield (result)
      future.asClient
    }

    def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
      val future = for {
        _ <- init().asInternal
        result <- Core.validate(model, ProfileNames.RAML10, MessageStyles.RAML).asInternal
      } yield (result)
      future.asClient
    }

    // def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    //   val future = for {
    //     _ <- init().asInternal
    //     result <- Future { new Raml10Resolver().resolve(model) }
    //   } yield (result)
    //   future.asClient
    // }
  }

  @JSExportAll
  @JSExportTopLevel("WebApiParser.raml08")
  object raml08 {
    def parseFile(url: String): ClientFuture[BaseUnit] = {
      val future = for {
        _ <- init().asInternal
        result <- new Raml08Parser().parseFileAsync(url).asInternal
      } yield (result)
      future.asClient
    }

    def parseString(content: String): ClientFuture[BaseUnit] = {
      val future = for {
        _ <- init().asInternal
        result <- new Raml08Parser().parseStringAsync(content).asInternal
      } yield (result)
      future.asClient
    }

    def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
      val future = for {
        _ <- init().asInternal
        result <- new Raml08Renderer().generateFile(model, url).asInternal
      } yield (result)
      future.asClient
    }

    def generateString(model: BaseUnit): ClientFuture[String] = {
      val future = for {
        _ <- init().asInternal
        result <- new Raml08Renderer().generateString(model).asInternal
      } yield (result)
      future.asClient
    }

    def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
      val future = for {
        _ <- init().asInternal
        result <- Core.validate(model, ProfileNames.RAML08, MessageStyles.RAML).asInternal
      } yield (result)
      future.asClient
    }

    // def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    //   val future = for {
    //     _ <- init().asInternal
    //     result <- Future { new Raml08Resolver().resolve(model) }
    //   } yield (result)
    //   future.asClient
    // }
  }

  @JSExportAll
  @JSExportTopLevel("WebApiParser.oas20")
  object oas20 {
    def parseFile(url: String): ClientFuture[BaseUnit] = {
      val future = for {
        _ <- init().asInternal
        result <- new Oas20Parser().parseFileAsync(url).asInternal
      } yield (result)
      future.asClient
    }

    def parseString(content: String): ClientFuture[BaseUnit] = {
      val future = for {
        _ <- init().asInternal
        result <- new Oas20Parser().parseStringAsync(content).asInternal
      } yield (result)
      future.asClient
    }

    def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
      val future = for {
        _ <- init().asInternal
        result <- new Oas20Renderer().generateFile(model, url).asInternal
      } yield (result)
      future.asClient
    }

    def generateString(model: BaseUnit): ClientFuture[String] = {
      val future = for {
        _ <- init().asInternal
        result <- new Oas20Renderer().generateString(model).asInternal
      } yield (result)
      future.asClient
    }

    def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
      val future = for {
        _ <- init().asInternal
        result <- Core.validate(model, ProfileNames.OAS20, MessageStyles.OAS).asInternal
      } yield (result)
      future.asClient
    }

    // def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    //   val future = for {
    //     _ <- init().asInternal
    //     result <- Future { new Oas20Resolver().resolve(model) }
    //   } yield (result)
    //   future.asClient
    // }

    // Specific to oas20 object
    def parseYamlFile(url: String): ClientFuture[BaseUnit] = {
      val future = for {
        _ <- init().asInternal
        result <- new Oas20YamlParser().parseFileAsync(url).asInternal
      } yield (result)
      future.asClient
    }

    def parseYamlString(content: String): ClientFuture[BaseUnit] = {
      val future = for {
        _ <- init().asInternal
        result <- new Oas20YamlParser().parseStringAsync(content).asInternal
      } yield (result)
      future.asClient
    }
  }

  @JSExportAll
  @JSExportTopLevel("WebApiParser.amfGraph")
  object amfgraph {
    def parseFile(url: String): ClientFuture[BaseUnit] = {
      val future = for {
        _ <- init().asInternal
        result <- new AmfGraphParser().parseFileAsync(url).asInternal
      } yield (result)
      future.asClient
    }

    def parseString(content: String): ClientFuture[BaseUnit] = {
      val future = for {
        _ <- init().asInternal
        result <- new AmfGraphParser().parseStringAsync(content).asInternal
      } yield (result)
      future.asClient
    }

    def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
      val future = for {
        _ <- init().asInternal
        result <- new AmfGraphRenderer().generateFile(model, url).asInternal
      } yield (result)
      future.asClient
    }

    def generateString(model: BaseUnit): ClientFuture[String] = {
      val future = for {
        _ <- init().asInternal
        result <- new AmfGraphRenderer().generateString(model).asInternal
      } yield (result)
      future.asClient
    }

    def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
      val future = for {
        _ <- init().asInternal
        result <- Core.validate(model, ProfileNames.AMF, MessageStyles.AMF).asInternal
      } yield (result)
      future.asClient
    }

    // def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    //   val future = for {
    //     _ <- init().asInternal
    //     result <- Future { new AmfGraphResolver().resolve(model) }
    //   } yield (result)
    //   future.asClient
    // }
  }
}
