package webapi

import amf.client.model.document.{
  EncodesModel, DeclaresModel,
  BaseUnit => AmfBaseUnit,
  Document => AmfDocument
}
import amf.client.model.domain.{NodeShape, DomainElement}
import amf.client.convert.{
  CoreBaseConverter,
  CoreBaseClientConverter,
  BidirectionalMatcher
}
import amf.core.model.document.{
  Document => InternalDocument,
  BaseUnit => InternalBaseUnit
}
import amf.core.unsafe.PlatformSecrets

import scala.scalajs.js.annotation._


@JSExportAll
trait BaseUnit extends AmfBaseUnit {
  def getDeclarationByName(name: String): String = {
    name + " foobar"
  }
}

@JSExportAll
class Document(override val _internal: InternalDocument) extends AmfDocument(_internal) with BaseUnit


trait WebApiBaseUnitConverter extends PlatformSecrets {
  implicit object WebApiBaseUnitMatcher extends BidirectionalMatcher[BaseUnit, BaseUnit] {
    override def asInternal(from: BaseUnit): BaseUnit = from
    override def asClient(from: BaseUnit): BaseUnit = from
  }
}

trait WebApiDocumentConverter extends PlatformSecrets {
  implicit object WebApiDocumentMatcher extends BidirectionalMatcher[Document, Document] {
    override def asInternal(from: Document): Document = from
    override def asClient(from: Document): Document = from
  }
}

object WebapiCoreClientConverters extends CoreBaseConverter with CoreBaseClientConverter
                                                            with WebApiBaseUnitConverter
                                                            with WebApiDocumentConverter
