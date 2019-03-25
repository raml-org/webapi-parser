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
  BidirectionalMatcher,
  BaseUnitConverter
}
import amf.core.model.document.{
  Document => InternalDocument,
  BaseUnit => InternalBaseUnit
}
import amf.core.unsafe.PlatformSecrets

import scala.scalajs.js.annotation._


@JSExportAll
trait BaseUnit extends AmfBaseUnit {
  override private[webapi] val _internal: InternalBaseUnit

  def getDeclarationByName(name: String): String = {
    name + " foobar"
  }
}

@JSExportAll
class Document(override val _internal: InternalDocument) extends AmfDocument(_internal) with BaseUnit


trait WebapiBaseUnitConverter extends PlatformSecrets {

  implicit object WebapiBaseUnitMatcher extends BidirectionalMatcher[InternalBaseUnit, BaseUnit] {
    // webapi.BaseUnit -> amf.core.model.document.BaseUnit
    override def asInternal(from: BaseUnit): InternalBaseUnit = from._internal

    // amf.core.model.document.BaseUnit -> webapi.BaseUnit
    override def asClient(from: InternalBaseUnit): BaseUnit = {
      from match {
        case d: InternalDocument => (new Document(d)).asInstanceOf[BaseUnit]
        case a: AmfDocument => (new Document(a.asInstanceOf[InternalDocument])).asInstanceOf[BaseUnit]
      }
    }
  }

  implicit object BaseUnitMatcher extends BidirectionalMatcher[InternalBaseUnit, AmfBaseUnit] {
    // amf.client.model.document.BaseUnit -> amf.core.model.document.BaseUnit
    override def asInternal(from: AmfBaseUnit): InternalBaseUnit = from._internal
  }

}

object WebapiClientConverters extends CoreBaseConverter with CoreBaseClientConverter
                                                        with WebapiBaseUnitConverter
