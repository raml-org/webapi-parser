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
  def getDeclarationByName(name: String): String = {
    name + " foobar"
  }
}

@JSExportAll
class Document(override val _internal: InternalDocument) extends AmfDocument(_internal) with BaseUnit
