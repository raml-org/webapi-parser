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


/** Utility object meant for internal use */
object WebApiParser {
  private var initialized: Boolean = false

  /** Initializes necessary plugins */
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

  /** Returns true if input represents path to local or remote file */
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

/** Provides methods for RAML 1.0 processing */
@JSExportAll
@JSExportTopLevel("WebApiParser.raml10")
object Raml10 {

  /** Parses RAML 1.0 content from string or url.
    *
    * @param urlOrContent File url/path or content string.
    * @return Parsed model.
    */
  def parse(urlOrContent: String): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      if(WebApiParser.isPath(urlOrContent)) {
        new Raml10Parser().parseFileAsync(urlOrContent).asInternal
      } else {
        new Raml10Parser().parseStringAsync(urlOrContent).asInternal
      }
    }).asClient
  }

  /** Generates file with RAML 1.0 content.
    *
    * @param model Parsed model.
    * @param url Generated file path.
    */
  def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
    WebApiParser.chainAfterInit(() => {
      new Raml10Renderer().generateFile(model, url).asInternal
    }).asClient
  }

  /** Generates string with RAML 1.0 content.
    *
    * @param model Parsed model.
    * @return Generated string.
    */
  def generateString(model: BaseUnit): ClientFuture[String] = {
    WebApiParser.chainAfterInit(() => {
      new Raml10Renderer().generateString(model).asInternal
    }).asClient
  }

  /** Validates parsed RAML 1.0 model.
    *
    * @param model Parsed model.
    * @return Validation report.
    */
  def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
    WebApiParser.chainAfterInit(() => {
      Core.validate(model, ProfileNames.RAML10, MessageStyles.RAML).asInternal
    }).asClient
  }

  /** Resolves parsed RAML 1.0 model.
    *
    * Resolution process includes resolving references to all types, libraries, etc.
    *
    * @param model Parsed model.
    * @return Resolved parsed model.
    */
  def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      Future {
        val resolved: InternalBaseUnit = new Raml10Resolver().resolve(model)
        resolved
      }
    }).asClient
  }
}

/** Provides methods for RAML 0.8 processing */
@JSExportAll
@JSExportTopLevel("WebApiParser.raml08")
object Raml08 {

  /** Parses RAML 0.8 content from string or url.
    *
    * @param urlOrContent File url/path or content string.
    * @return Parsed model.
    */
  def parse(urlOrContent: String): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      if(WebApiParser.isPath(urlOrContent)) {
        new Raml08Parser().parseFileAsync(urlOrContent).asInternal
      } else {
        new Raml08Parser().parseStringAsync(urlOrContent).asInternal
      }
    }).asClient
  }

  /** Generates file with RAML 0.8 content.
    *
    * @param model Parsed model.
    * @param url Generated file path.
    */
  def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
    WebApiParser.chainAfterInit(() => {
      new Raml08Renderer().generateFile(model, url).asInternal
    }).asClient
  }

  /** Generates string with RAML 0.8 content.
    *
    * @param model Parsed model.
    * @return Generated string.
    */
  def generateString(model: BaseUnit): ClientFuture[String] = {
    WebApiParser.chainAfterInit(() => {
      new Raml08Renderer().generateString(model).asInternal
    }).asClient
  }

  /** Validates parsed RAML 0.8 model.
    *
    * @param model Parsed model.
    * @return Validation report.
    */
  def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
    WebApiParser.chainAfterInit(() => {
      Core.validate(model, ProfileNames.RAML08, MessageStyles.RAML).asInternal
    }).asClient
  }

  /** Resolves parsed RAML 0.8 model.
    *
    * Resolution process includes resolving references to all types, libraries, etc.
    *
    * @param model Parsed model.
    * @return Resolved parsed model.
    */
  def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      Future {
        val resolved: InternalBaseUnit = new Raml08Resolver().resolve(model)
        resolved
      }
    }).asClient
  }
}

/** Provides methods for OAS 2.0 processing */
@JSExportAll
@JSExportTopLevel("WebApiParser.oas20")
object Oas20 {

  /** Parses OAS 2.0 JSON content from string or url.
    *
    * @param urlOrContent File url/path or content string.
    * @return Parsed model.
    */
  def parse(urlOrContent: String): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      if(WebApiParser.isPath(urlOrContent)) {
        new Oas20Parser().parseFileAsync(urlOrContent).asInternal
      } else {
        new Oas20Parser().parseStringAsync(urlOrContent).asInternal
      }
    }).asClient
  }

  /** Generates file with OAS 2.0 JSON content.
    *
    * @param model Parsed model.
    * @param url Generated file path.
    */
  def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
    WebApiParser.chainAfterInit(() => {
      new Oas20Renderer().generateFile(model, url).asInternal
    }).asClient
  }

  /** Generates string with OAS 2.0 JSON content.
    *
    * @param model Parsed model.
    * @return Generated string.
    */
  def generateString(model: BaseUnit): ClientFuture[String] = {
    WebApiParser.chainAfterInit(() => {
      new Oas20Renderer().generateString(model).asInternal
    }).asClient
  }

  /** Validates parsed OAS 2.0 model.
    *
    * @param model Parsed model.
    * @return Validation report.
    */
  def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
    WebApiParser.chainAfterInit(() => {
      Core.validate(model, ProfileNames.OAS20, MessageStyles.OAS).asInternal
    }).asClient
  }

  /** Resolves parsed OAS 2.0 model.
    *
    * Resolution process includes resolving references to all types, libraries, etc.
    *
    * @param model Parsed model.
    * @return Resolved parsed model.
    */
  def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      Future {
        val resolved: InternalBaseUnit = new Oas20Resolver().resolve(model)
        resolved
      }
    }).asClient
  }

  /** Parses OAS 2.0 YAML content from string or url.
    *
    * @param urlOrContent File url/path or content string.
    * @return Parsed model.
    */
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

/** Provides methods for AMF Graph processing */
@JSExportAll
@JSExportTopLevel("WebApiParser.amfGraph")
object AmfGraph {

  /** Parses AMF Graph content from string or url.
    *
    * @param urlOrContent File url/path or content string.
    * @return Parsed model.
    */
  def parse(urlOrContent: String): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      if(WebApiParser.isPath(urlOrContent)) {
        new AmfGraphParser().parseFileAsync(urlOrContent).asInternal
      } else {
        new AmfGraphParser().parseStringAsync(urlOrContent).asInternal
      }
    }).asClient
  }

  /** Generates file with AMF Graph content.
    *
    * @param model Parsed model.
    * @param url Generated file path.
    */
  def generateFile(model: BaseUnit, url: String): ClientFuture[Unit] = {
    WebApiParser.chainAfterInit(() => {
      new AmfGraphRenderer().generateFile(model, url).asInternal
    }).asClient
  }

  /** Generates string with AMF Graph content.
    *
    * @param model Parsed model.
    * @return Generated string.
    */
  def generateString(model: BaseUnit): ClientFuture[String] = {
    WebApiParser.chainAfterInit(() => {
      new AmfGraphRenderer().generateString(model).asInternal
    }).asClient
  }

  /** Validates parsed AMF Graph model.
    *
    * @param model Parsed model.
    * @return Validation report.
    */
  def validate(model: BaseUnit): ClientFuture[ValidationReport] = {
    WebApiParser.chainAfterInit(() => {
      Core.validate(model, ProfileNames.AMF, MessageStyles.AMF).asInternal
    }).asClient
  }

  /** Resolves parsed AMF Graph model.
    *
    * Resolution process includes resolving references to all types, libraries, etc.
    *
    * @param model Parsed model.
    * @return Resolved parsed model.
    */
  def resolve(model: BaseUnit): ClientFuture[BaseUnit] = {
    WebApiParser.chainAfterInit(() => {
      Future {
        val resolved: InternalBaseUnit = new AmfGraphResolver().resolve(model)
        resolved
      }
    }).asClient
  }
}
