package webapi

import amf.client.model.document.BaseUnit
import amf.client.convert.{CoreBaseClientConverter, BidirectionalMatcher}
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

import scala.concurrent._
import scala.language.implicitConversions
import ExecutionContext.Implicits.global


/** WebApiBaseUnit <-> BaseUnit implicit converters. */
object WebApiClientConverters extends CoreBaseClientConverter {
  /**
    * Converts WebApiBaseUnit <-> WebApiBaseUnit wrapped in ClientFuture.
    * Just to make AMF implicit converters wrap/unwrap WebApiBaseUnit in/from
    * ClientFuture.
    */
  implicit object WebApiBaseUnitMatcher extends BidirectionalMatcher[WebApiBaseUnit, WebApiBaseUnit] {
    override def asInternal(from: WebApiBaseUnit): WebApiBaseUnit = from
    override def asClient(from: WebApiBaseUnit): WebApiBaseUnit = from
  }

  /**
    * Converts BaseUnit -> WebApiBaseUnit wrapped in ClientFuture.
    * First converts to concrete implementation and wraps in custom WebApi model class.
    * Then outputs model as custom trait type.
    */
  implicit def ClientBaseUnit2WebApiBaseUnit(bu: ClientFuture[BaseUnit]): ClientFuture[WebApiBaseUnit] = {
    (bu.asInternal map { model =>
      val wapModel = model match {
        case ext: InternalExtension                 => new WebApiExtension(ext)
        case ovr: InternalOverlay                   => new WebApiOverlay(ovr)
        case doc: InternalDocument                  => new WebApiDocument(doc)
        case mod: InternalModule                    => new WebApiModule(mod)
        case exf: InternalExternalFragment          => new WebApiExternalFragment(exf)
        case doi: InternalDocumentationItem         => new WebApiDocumentationItem(doi)
        case dat: InternalDataType                  => new WebApiDataType(dat)
        case nex: InternalNamedExample              => new WebApiNamedExample(nex)
        case rtf: InternalResourceTypeFragment      => new WebApiResourceTypeFragment(rtf)
        case trf: InternalTraitFragment             => new WebApiTraitFragment(trf)
        case atd: InternalAnnotationTypeDeclaration => new WebApiAnnotationTypeDeclaration(atd)
        case ssf: InternalSecuritySchemeFragment    => new WebApiSecuritySchemeFragment(ssf)
        case pfr: InternalPayloadFragment           => new WebApiPayloadFragment(pfr)
        case voc: InternalVocabulary                => new WebApiVocabulary(voc)
      }
      wapModel.asInstanceOf[WebApiBaseUnit]
    }).asClient
  }

  /**
    * Converts WebApiBaseUnit -> BaseUnit wrapped in ClientFuture.
    */
  implicit def WebApiBaseUnit2ClientBaseUnit(bu: ClientFuture[WebApiBaseUnit]): ClientFuture[BaseUnit] = {
    (bu.asInternal map { model =>
      model.asInstanceOf[InternalBaseUnit]
    }).asClient
  }
}
