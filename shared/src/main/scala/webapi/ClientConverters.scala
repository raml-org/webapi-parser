package webapi

import amf.client.model.document.BaseUnit
import amf.client.convert.{CoreBaseClientConverter, BidirectionalMatcher}
import amf.core.model.document.{
  BaseUnit => InternalBaseUnit,
  Document => InternalDocument,
  Module => InternalModule
}

import scala.concurrent._
import scala.language.implicitConversions
import ExecutionContext.Implicits.global


object WebApiClientConverters extends CoreBaseClientConverter {
  implicit object WebApiBaseUnitMatcher extends BidirectionalMatcher[WebApiBaseUnit, WebApiBaseUnit] {
    override def asInternal(from: WebApiBaseUnit): WebApiBaseUnit = from
    override def asClient(from: WebApiBaseUnit): WebApiBaseUnit = from
  }

  implicit def ClientBaseUnit2WebApiBaseUnit(bu: ClientFuture[BaseUnit]): ClientFuture[WebApiBaseUnit] = {
    (bu.asInternal map { model =>
      model match {
        case doc: InternalDocument => (new WebApiDocument(doc)).asInstanceOf[WebApiBaseUnit]
        case mod: InternalModule => (new WebApiModule(mod)).asInstanceOf[WebApiBaseUnit]
      }
    }).asClient
  }

  implicit def WebApiBaseUnit2ClientBaseUnit(bu: ClientFuture[WebApiBaseUnit]): ClientFuture[BaseUnit] = {
    (bu.asInternal map { model =>
      model.asInstanceOf[InternalBaseUnit]
    }).asClient
  }
}
