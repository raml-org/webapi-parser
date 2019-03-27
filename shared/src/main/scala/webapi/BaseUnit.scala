package webapi

import amf.client.model.document.{
  BaseUnit, Document, Module
}
import amf.core.model.document.{
  BaseUnit => InternalBaseUnit,
  Document => InternalDocument,
  Module => InternalModule
}

import scala.scalajs.js.annotation._


@JSExportAll
trait WebApiBaseUnit extends BaseUnit {
  override val _internal: InternalBaseUnit

  def getDeclarationByName(name: String): String = {
    name + " foobar"
  }
}

@JSExportAll
@JSExportTopLevel("webapi.WebApiDocument")
class WebApiDocument(override val _internal: InternalDocument) extends Document(_internal) with WebApiBaseUnit

@JSExportAll
@JSExportTopLevel("webapi.WebApiModule")
class WebApiModule(override val _internal: InternalModule) extends Module(_internal) with WebApiBaseUnit
