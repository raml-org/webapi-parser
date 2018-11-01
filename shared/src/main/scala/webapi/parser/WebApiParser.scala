package webapi.parser

import amf.client.parse.{Raml10Parser}

import scala.scalajs.js.annotation._

@JSExportAll
@JSExportTopLevel("WebApiParser")
object WebApiParser {

  def raml10Parser(): Raml10Parser = new Raml10Parser()

}
