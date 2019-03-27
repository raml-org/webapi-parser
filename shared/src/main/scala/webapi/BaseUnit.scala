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
trait WebApiBaseUnit extends BaseUnit {
  override val _internal: InternalBaseUnit

  def getDeclarationByName(name: String): String = {
    name + " foobar"
  }
}

@JSExportAll
class WebApiDocument(override val _internal: InternalDocument) extends Document(_internal) with WebApiBaseUnit


object WebApiClientConverters extends CoreBaseClientConverter {
  implicit object WebApiBaseUnitMatcher extends BidirectionalMatcher[WebApiBaseUnit, WebApiBaseUnit] {
    override def asInternal(from: WebApiBaseUnit): WebApiBaseUnit = from
    override def asClient(from: WebApiBaseUnit): WebApiBaseUnit = from
  }

  implicit def ClientBaseUnit2WebApiBaseUnit(bu: ClientFuture[BaseUnit]): ClientFuture[WebApiBaseUnit] = {
    (bu.asInternal map { model =>
      model match {
        case doc: InternalDocument => (new WebApiDocument(doc)).asInstanceOf[WebApiBaseUnit]
      }
    }).asClient
  }

  implicit def WebApiBaseUnit2ClientBaseUnit(bu: ClientFuture[WebApiBaseUnit]): ClientFuture[BaseUnit] = {
    (bu.asInternal map { model =>
      model.asInstanceOf[InternalBaseUnit]
    }).asClient
  }
}
