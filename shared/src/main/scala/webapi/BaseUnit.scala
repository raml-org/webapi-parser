package webapi

import amf.client.model.document.{
  BaseUnit,
  Document,
  Module,
  // PayloadFragment,
  ExternalFragment,
  Vocabulary,
  Extension,
  Overlay,
  DocumentationItem,
  DataType,
  NamedExample,
  ResourceTypeFragment,
  TraitFragment,
  AnnotationTypeDeclaration,
  SecuritySchemeFragment
}
import amf.core.model.document.{
  BaseUnit => InternalBaseUnit,
  Document => InternalDocument,
  Module => InternalModule,
  // PayloadFragment => InternalPayloadFragment,
  ExternalFragment => InternalExternalFragment
}
import amf.plugins.document.vocabularies.model.document.{
  Vocabulary => InternalVocabulary
}
import amf.plugins.document.webapi.model.{
  Extension => InternalExtension,
  Overlay => InternalOverlay,
  DocumentationItemFragment => InternalDocumentationItemFragment,
  DataTypeFragment => InternalDataTypeFragment,
  NamedExampleFragment => InternalNamedExampleFragment,
  ResourceTypeFragment => InternalResourceTypeFragment,
  TraitFragment => InternalTraitFragment,
  AnnotationTypeDeclarationFragment => InternalAnnotationTypeDeclarationFragment,
  SecuritySchemeFragment => InternalSecuritySchemeFragment
}

import scala.scalajs.js.annotation._


@JSExportAll
trait WebApiBaseUnit extends BaseUnit {
  override val _internal: InternalBaseUnit

  def getDeclarationByName(name: String): String = {
    name + " foobar"
  }
}

@JSExportAll
@JSExportTopLevel("webapi.WebApiDocument")
class WebApiDocument(override val _internal: InternalDocument)
    extends Document(_internal) with WebApiBaseUnit

@JSExportAll
@JSExportTopLevel("webapi.WebApiModule")
class WebApiModule(override val _internal: InternalModule)
    extends Module(_internal) with WebApiBaseUnit

@JSExportAll
@JSExportTopLevel("webapi.WebApiExternalFragment")
class WebApiExternalFragment(override val _internal: InternalExternalFragment)
    extends ExternalFragment(_internal) with WebApiBaseUnit

@JSExportAll
@JSExportTopLevel("webapi.WebApiExtension")
class WebApiExtension(override val _internal: InternalExtension)
    extends Extension(_internal) with WebApiBaseUnit

@JSExportAll
@JSExportTopLevel("webapi.WebApiOverlay")
class WebApiOverlay(override val _internal: InternalOverlay)
    extends Overlay(_internal) with WebApiBaseUnit
