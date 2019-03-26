package webapi

import amf.client.model.document.{
  EncodesModel, DeclaresModel, BaseUnit,
  Document
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
trait WebapiBaseUnit extends BaseUnit {
  override private[webapi] val _internal: InternalBaseUnit

  def getDeclarationByName(name: String): String = {
    name + " foobar"
  }
}

@JSExportAll
class WebapiDocument(override val _internal: InternalDocument) extends Document(_internal) with WebapiBaseUnit


trait WebapiBaseUnitConverter extends PlatformSecrets {

  implicit object WebapiBaseUnitMatcher extends BidirectionalMatcher[InternalBaseUnit, WebapiBaseUnit] {
    // webapi.BaseUnit -> amf.core.model.document.BaseUnit
    override def asInternal(from: WebapiBaseUnit): InternalBaseUnit = from._internal

    // amf.core.model.document.BaseUnit -> webapi.BaseUnit
    override def asClient(from: InternalBaseUnit): WebapiBaseUnit = {
      from match {
        case d: InternalDocument => (new WebapiDocument(d)).asInstanceOf[WebapiBaseUnit]
        case a: Document => (new WebapiDocument(a.asInstanceOf[InternalDocument])).asInstanceOf[WebapiBaseUnit]
      }
    }
  }

  implicit object BaseUnitMatcher extends BidirectionalMatcher[InternalBaseUnit, BaseUnit] {
    // amf.client.model.document.BaseUnit -> amf.core.model.document.BaseUnit
    override def asInternal(from: BaseUnit): InternalBaseUnit = from.asInstanceOf[InternalBaseUnit]
  }

}

object WebapiClientConverters extends CoreBaseConverter with CoreBaseClientConverter
                                                        with WebapiBaseUnitConverter
