package webapi

import amf.client.model.document.{
  EncodesModel, DeclaresModel,
  BaseUnit => AmfBaseUnit,
  Document => AmfDocument
}
import amf.client.model.domain.{NodeShape, DomainElement}
import amf.client.convert.CoreClientConverters._
import amf.core.model.document.{Document => InternalDocument}

import scala.scalajs.js.annotation._


@JSExportAll
trait BaseUnit extends AmfBaseUnit {
  def getDeclarationByName(name: String): String = {
    name + " foobar"
  }
}

@JSExportAll
class Document(_internal: InternalDocument) extends AmfDocument(_internal) with BaseUnit {

}


// @JSExportAll
// class Document(private[amf] val _internal: InternalDocument) extends BaseUnit with EncodesModel with DeclaresModel {

//   @JSExportTopLevel("webapi.model.document.Document")
//   def this() = this(InternalDocument())

//   @JSExportTopLevel("webapi.model.document.Document")
//   def this(encoding: DomainElement) = this(InternalDocument().withEncodes(encoding))
// }
