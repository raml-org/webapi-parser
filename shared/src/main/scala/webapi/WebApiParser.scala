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

object WebApiParser {
  var initialized: Boolean = false

  def init(): ClientFuture[Unit] = {
    if(initialized) {
      val emptyFuture: Future[Unit] = Future {}
      emptyFuture.asClient
    } else {
      initialized = true
      WebApi.register()
      AMFValidation.register()
      amf.Core.registerPlugin(PayloadValidatorPlugin)
      amf.Core.init()
    }
  }

  def isPath(inp: String): Boolean = {
    (inp.startsWith("http://") || inp.startsWith("https://") || inp.startsWith("file:"))
  }

  def chainAfterInit[T](func: () => Future[T]): Future[T] = {
    for {
      _ <- init().asInternal
      result <- func()
    } yield (result)
  }
}

@JSExportAll
@JSExportTopLevel("WebApiParser.raml10")
object Raml10 {
  def parse(urlOrContent: String): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      if(WebApiParser.isPath(urlOrContent)) {
        new Raml10Parser().parseFileAsync(urlOrContent).asInternal
      } else {
        new Raml10Parser().parseStringAsync(urlOrContent).asInternal
      }
    }).asClient
  }

  def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
    WebApiParser.chainAfterInit(() => {
      new Raml10Renderer().generateFile(model, url).asInternal
    }).asClient
  }

  def generateString(model: BaseUnit): ClientFuture[String] = {
    WebApiParser.chainAfterInit(() => {
      new Raml10Renderer().generateString(model).asInternal
    }).asClient
  }

  def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
    WebApiParser.chainAfterInit(() => {
      Core.validate(model, ProfileNames.RAML10, MessageStyles.RAML).asInternal
    }).asClient
  }

  def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      Future {
        val resolved: InternalBaseUnit = new Raml10Resolver().resolve(model)
        resolved
      }
    }).asClient
  }
}

@JSExportAll
@JSExportTopLevel("WebApiParser.raml08")
object Raml08 {
  def parse(urlOrContent: String): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      if(WebApiParser.isPath(urlOrContent)) {
        new Raml08Parser().parseFileAsync(urlOrContent).asInternal
      } else {
        new Raml08Parser().parseStringAsync(urlOrContent).asInternal
      }
    }).asClient
  }

  def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
    WebApiParser.chainAfterInit(() => {
      new Raml08Renderer().generateFile(model, url).asInternal
    }).asClient
  }

  def generateString(model: BaseUnit): ClientFuture[String] = {
    WebApiParser.chainAfterInit(() => {
      new Raml08Renderer().generateString(model).asInternal
    }).asClient
  }

  def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
    WebApiParser.chainAfterInit(() => {
      Core.validate(model, ProfileNames.RAML08, MessageStyles.RAML).asInternal
    }).asClient
  }

  def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      Future {
        val resolved: InternalBaseUnit = new Raml08Resolver().resolve(model)
        resolved
      }
    }).asClient
  }
}

@JSExportAll
@JSExportTopLevel("WebApiParser.oas20")
object Oas20 {
  def parse(urlOrContent: String): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      if(WebApiParser.isPath(urlOrContent)) {
        new Oas20Parser().parseFileAsync(urlOrContent).asInternal
      } else {
        new Oas20Parser().parseStringAsync(urlOrContent).asInternal
      }
    }).asClient
  }

  def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
    WebApiParser.chainAfterInit(() => {
      new Oas20Renderer().generateFile(model, url).asInternal
    }).asClient
  }

  def generateString(model: BaseUnit): ClientFuture[String] = {
    WebApiParser.chainAfterInit(() => {
      new Oas20Renderer().generateString(model).asInternal
    }).asClient
  }

  def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
    WebApiParser.chainAfterInit(() => {
      Core.validate(model, ProfileNames.OAS20, MessageStyles.OAS).asInternal
    }).asClient
  }

  def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      Future {
        val resolved: InternalBaseUnit = new Oas20Resolver().resolve(model)
        resolved
      }
    }).asClient
  }

  def parseYaml(urlOrContent: String): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      if(WebApiParser.isPath(urlOrContent)) {
        new Oas20YamlParser().parseFileAsync(urlOrContent).asInternal
      } else {
        new Oas20YamlParser().parseStringAsync(urlOrContent).asInternal
      }
    }).asClient
  }
}

@JSExportAll
@JSExportTopLevel("WebApiParser.amfGraph")
object AmfGraph {
  def parse(urlOrContent: String): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      if(WebApiParser.isPath(urlOrContent)) {
        new AmfGraphParser().parseFileAsync(urlOrContent).asInternal
      } else {
        new AmfGraphParser().parseStringAsync(urlOrContent).asInternal
      }
    }).asClient
  }

  def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
    WebApiParser.chainAfterInit(() => {
      new AmfGraphRenderer().generateFile(model, url).asInternal
    }).asClient
  }

  def generateString(model: BaseUnit): ClientFuture[String] = {
    WebApiParser.chainAfterInit(() => {
      new AmfGraphRenderer().generateString(model).asInternal
    }).asClient
  }

  def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
    WebApiParser.chainAfterInit(() => {
      Core.validate(model, ProfileNames.AMF, MessageStyles.AMF).asInternal
    }).asClient
  }

  def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      Future {
        val resolved: InternalBaseUnit = new AmfGraphResolver().resolve(model)
        resolved
      }
    }).asClient
  }
}
