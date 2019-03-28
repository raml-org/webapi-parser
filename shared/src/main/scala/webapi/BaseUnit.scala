package webapi

import amf.client.model.document.{
  BaseUnit,
  Document,
  Module,
  PayloadFragment,
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
  PayloadFragment => InternalPayloadFragment,
  ExternalFragment => InternalExternalFragment
}
import amf.plugins.document.vocabularies.model.document.{
  Vocabulary => InternalVocabulary
}
import amf.plugins.document.webapi.model.{
  Extension => InternalExtension,
  Overlay => InternalOverlay,
  DocumentationItemFragment => InternalDocumentationItem,
  DataTypeFragment => InternalDataType,
  NamedExampleFragment => InternalNamedExample,
  ResourceTypeFragment => InternalResourceTypeFragment,
  TraitFragment => InternalTraitFragment,
  AnnotationTypeDeclarationFragment => InternalAnnotationTypeDeclaration,
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

@JSExportAll
@JSExportTopLevel("webapi.WebApiDocumentationItem")
class WebApiDocumentationItem(override val _internal: InternalDocumentationItem)
    extends DocumentationItem(_internal) with WebApiBaseUnit

@JSExportAll
@JSExportTopLevel("webapi.WebApiDataType")
class WebApiDataType(override val _internal: InternalDataType)
    extends DataType(_internal) with WebApiBaseUnit

@JSExportAll
@JSExportTopLevel("webapi.WebApiNamedExample")
class WebApiNamedExample(override val _internal: InternalNamedExample)
    extends NamedExample(_internal) with WebApiBaseUnit

@JSExportAll
@JSExportTopLevel("webapi.WebApiResourceTypeFragment")
class WebApiResourceTypeFragment(override val _internal: InternalResourceTypeFragment)
    extends ResourceTypeFragment(_internal) with WebApiBaseUnit

@JSExportAll
@JSExportTopLevel("webapi.WebApiTraitFragment")
class WebApiTraitFragment(override val _internal: InternalTraitFragment)
    extends TraitFragment(_internal) with WebApiBaseUnit

@JSExportAll
@JSExportTopLevel("webapi.WebApiAnnotationTypeDeclaration")
class WebApiAnnotationTypeDeclaration(override val _internal: InternalAnnotationTypeDeclaration)
    extends AnnotationTypeDeclaration(_internal) with WebApiBaseUnit

@JSExportAll
@JSExportTopLevel("webapi.WebApiSecuritySchemeFragment")
class WebApiSecuritySchemeFragment(override val _internal: InternalSecuritySchemeFragment)
    extends SecuritySchemeFragment(_internal) with WebApiBaseUnit

@JSExportAll
@JSExportTopLevel("webapi.WebApiPayloadFragment")
class WebApiPayloadFragment(override val _internal: InternalPayloadFragment)
    extends PayloadFragment(_internal) with WebApiBaseUnit

@JSExportAll
@JSExportTopLevel("webapi.WebApiVocabulary")
class WebApiVocabulary(override val _internal: InternalVocabulary)
    extends Vocabulary(_internal) with WebApiBaseUnit
