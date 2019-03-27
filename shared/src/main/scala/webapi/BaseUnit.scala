package webapi

import amf.client.model.document.{BaseUnit, Document}
import amf.client.convert.{CoreBaseClientConverter, BidirectionalMatcher}
import amf.core.model.document.{
  Document => InternalDocument,
  BaseUnit => InternalBaseUnit
}

import scala.scalajs.js.annotation._
import scala.concurrent._
import scala.language.implicitConversions
import ExecutionContext.Implicits.global


@JSExportAll
trait WebapiBaseUnit extends BaseUnit {
  override val _internal: InternalBaseUnit

  def getDeclarationByName(name: String): String = {
    name + " foobar"
  }
}

@JSExportAll
class WebapiDocument(override val _internal: InternalDocument) extends Document(_internal) with WebapiBaseUnit


object WebapiClientConverters extends CoreBaseClientConverter {
  implicit object WebApiBaseUnitMatcher extends BidirectionalMatcher[WebapiBaseUnit, WebapiBaseUnit] {
    override def asInternal(from: WebapiBaseUnit): WebapiBaseUnit = from
    override def asClient(from: WebapiBaseUnit): WebapiBaseUnit = from
  }

  implicit def ClientBaseUnit2WebapiBaseUnit(bu: ClientFuture[BaseUnit]): ClientFuture[WebapiBaseUnit] = {
    (bu.asInternal map { model =>
      (new WebapiDocument(model.asInstanceOf[InternalDocument])).asInstanceOf[WebapiBaseUnit]
    }).asClient
  }

  implicit def WebapiBaseUnit2ClientBaseUnit(bu: ClientFuture[WebapiBaseUnit]): ClientFuture[BaseUnit] = {
    (bu.asInternal map { model =>
      model.asInstanceOf[InternalBaseUnit]
    }).asClient
  }
}

// from match {
//   case d: InternalDocument => (new WebapiDocument(d)).asInstanceOf[WebapiBaseUnit]
//   case a: Document => (new WebapiDocument(a.asInstanceOf[InternalDocument])).asInstanceOf[WebapiBaseUnit]
// }
