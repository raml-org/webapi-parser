package webapi

import amf.client.model.document.{
  EncodesModel, DeclaresModel,
  BaseUnit => AmfBaseUnit,
  Document => AmfDocument
}
import amf.client.model.domain.{NodeShape, DomainElement}
import amf.client.convert.CoreClientConverters._
import amf.core.model.document.{Document => InternalDocument}
import amf.core.model.document.{BaseUnit => InternalBaseUnit}

import scala.scalajs.js.annotation._


@JSExportAll
trait BaseUnit extends AmfBaseUnit {
  // override val _internal: InternalBaseUnit

  def getDeclarationByName(name: String): String = {
    name + " foobar"
  }
}

@JSExportAll
class Document(val _internal: AmfDocument) extends BaseUnit {
  @JSExportTopLevel("webapi.model.document.Document")
  def this() = this(new AmfDocument())

  @JSExportTopLevel("webapi.model.document.Document")
  def this(encoding: DomainElement) = this(new AmfDocument().withEncodes(encoding))
}
