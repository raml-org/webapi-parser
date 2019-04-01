package webapi

import webapi.WebApiClientConverters._

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
import amf.client.model.domain.{DomainElement, NodeShape}
import amf.plugins.domain.shapes.models.{NodeShape => InternalNodeShape}

import scala.scalajs.js.annotation._
import collection.mutable.Map


/**
  * Custom BaseUnit subclass which implements utility methods.
  */
@JSExportAll
trait WebApiBaseUnit extends BaseUnit {
  override val _internal: InternalBaseUnit

  /** Gets declaration by name.
    *
    * @param name String name of declaration to look for.
    * @return Found declaration as NodeShape.
    */
  def getDeclarationByName(name: String): NodeShape = {
    var nodesMap = Map[String, NodeShape]()
    val elements: ClientList[DomainElement] = findByType("http://www.w3.org/ns/shacl#NodeShape")
    elements.asInternal foreach {
      element => {
        var shape = element match {
          case nsh: NodeShape         => nsh
          case ins: InternalNodeShape => new NodeShape(ins)
        }
        nodesMap += (shape.name.value() -> shape)
      }
    }
    nodesMap.get(name) match {
      case Some(declaration) => declaration
      case None => throw new Exception(s"Declaration with name '$name' not found")
    }
  }
}


/** Subclass of Document inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiDocument")
class WebApiDocument(override val _internal: InternalDocument)
    extends Document(_internal) with WebApiBaseUnit


/** Subclass of Module inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiModule")
class WebApiModule(override val _internal: InternalModule)
    extends Module(_internal) with WebApiBaseUnit


/** Subclass of ExternalFragment inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiExternalFragment")
class WebApiExternalFragment(override val _internal: InternalExternalFragment)
    extends ExternalFragment(_internal) with WebApiBaseUnit


/** Subclass of Extension inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiExtension")
class WebApiExtension(override val _internal: InternalExtension)
    extends Extension(_internal) with WebApiBaseUnit


/** Subclass of Overlay inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiOverlay")
class WebApiOverlay(override val _internal: InternalOverlay)
    extends Overlay(_internal) with WebApiBaseUnit


/** Subclass of DocumentationItem inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiDocumentationItem")
class WebApiDocumentationItem(override val _internal: InternalDocumentationItem)
    extends DocumentationItem(_internal) with WebApiBaseUnit


/** Subclass of DataType inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiDataType")
class WebApiDataType(override val _internal: InternalDataType)
    extends DataType(_internal) with WebApiBaseUnit


/** Subclass of NamedExample inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiNamedExample")
class WebApiNamedExample(override val _internal: InternalNamedExample)
    extends NamedExample(_internal) with WebApiBaseUnit


/** Subclass of ResourceTypeFragment inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiResourceTypeFragment")
class WebApiResourceTypeFragment(override val _internal: InternalResourceTypeFragment)
    extends ResourceTypeFragment(_internal) with WebApiBaseUnit


/** Subclass of TraitFragment inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiTraitFragment")
class WebApiTraitFragment(override val _internal: InternalTraitFragment)
    extends TraitFragment(_internal) with WebApiBaseUnit


/** Subclass of AnnotationTypeDeclaration inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiAnnotationTypeDeclaration")
class WebApiAnnotationTypeDeclaration(override val _internal: InternalAnnotationTypeDeclaration)
    extends AnnotationTypeDeclaration(_internal) with WebApiBaseUnit


/** Subclass of SecuritySchemeFragment inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiSecuritySchemeFragment")
class WebApiSecuritySchemeFragment(override val _internal: InternalSecuritySchemeFragment)
    extends SecuritySchemeFragment(_internal) with WebApiBaseUnit


/** Subclass of PayloadFragment inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiPayloadFragment")
class WebApiPayloadFragment(override val _internal: InternalPayloadFragment)
    extends PayloadFragment(_internal) with WebApiBaseUnit


/** Subclass of Vocabulary inheriting WebApiBaseUnit utility methods. */
@JSExportAll
@JSExportTopLevel("webapi.WebApiVocabulary")
class WebApiVocabulary(override val _internal: InternalVocabulary)
    extends Vocabulary(_internal) with WebApiBaseUnit
