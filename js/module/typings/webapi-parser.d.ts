import {model, client} from 'amf-client-js';

declare module "webapi-parser" {

  /** Custom BaseUnit subclass which implements utility methods. */
  abstract class WebApiBaseUnit extends model.document.BaseUnit {

    /** Gets declaration by name.
     *
     * @param name String name of declaration to look for.
     * @return Found declaration as NodeShape.
     */
    getDeclarationByName(name: string): model.domain.AnyShape
  }

  abstract class WebApiBaseUnitWithDeclaresModel extends WebApiBaseUnit implements model.document.DeclaresModel {
    /* DeclaresModel methods */
    declares: model.domain.DomainElement[]

    withDeclaredElement(declared: model.domain.DomainElement): this

    withDeclares(declares: model.domain.DomainElement[]): this
  }

  abstract class WebApiBaseUnitWithEncodesModel extends WebApiBaseUnit implements model.document.EncodesModel {
    /* EncodesModel methods */
    encodes: model.domain.DomainElement

    withEncodes(enocdes: model.domain.DomainElement): this
  }

  abstract class WebApiBaseUnitWithDeclaresModelAndEncodesModel extends WebApiBaseUnit implements model.document.DeclaresModel, model.document.EncodesModel {
    /* DeclaresModel methods */
    declares: model.domain.DomainElement[]

    withDeclaredElement(declared: model.domain.DomainElement): this

    withDeclares(declares: model.domain.DomainElement[]): this

    /* EncodesModel methods */
    encodes: model.domain.DomainElement

    withEncodes(enocdes: model.domain.DomainElement): this
  }

  namespace webapi {
    /** Subclass of Document inheriting WebApiBaseUnit utility methods. */
    export class WebApiDocument extends WebApiBaseUnitWithDeclaresModelAndEncodesModel {
    }

    /** Subclass of Module inheriting WebApiBaseUnit utility methods. */
    export class WebApiModule extends WebApiBaseUnitWithDeclaresModel {
    }

    /** Subclass of ExternalFragment inheriting WebApiBaseUnit utility methods. */
    export class WebApiExternalFragment extends WebApiBaseUnitWithEncodesModel {
    }

    /** Subclass of Extension inheriting WebApiBaseUnit utility methods. */
    export class WebApiExtension extends WebApiBaseUnitWithEncodesModel {
    }

    /** Subclass of Overlay inheriting WebApiBaseUnit utility methods. */
    export class WebApiOverlay extends WebApiBaseUnitWithEncodesModel {
    }

    /** Subclass of DocumentationItem inheriting WebApiBaseUnit utility methods. */
    export class WebApiDocumentationItem extends WebApiBaseUnitWithEncodesModel {
    }

    /** Subclass of DataType inheriting WebApiBaseUnit utility methods. */
    export class WebApiDataType extends WebApiBaseUnitWithEncodesModel {
    }

    /** Subclass of NamedExample inheriting WebApiBaseUnit utility methods. */
    export class WebApiNamedExample extends WebApiBaseUnitWithEncodesModel {
    }

    /** Subclass of ResourceTypeFragment inheriting WebApiBaseUnit utility methods. */
    export class WebApiResourceTypeFragment extends WebApiBaseUnitWithEncodesModel {
    }

    /** Subclass of TraitFragment inheriting WebApiBaseUnit utility methods. */
    export class WebApiTraitFragment extends WebApiBaseUnitWithEncodesModel {
    }

    /** Subclass of AnnotationTypeDeclaration inheriting WebApiBaseUnit utility methods. */
    export class WebApiAnnotationTypeDeclaration extends WebApiBaseUnitWithEncodesModel {
    }

    /** Subclass of SecuritySchemeFragment inheriting WebApiBaseUnit utility methods. */
    export class WebApiSecuritySchemeFragment extends WebApiBaseUnitWithEncodesModel {
    }

    /** Subclass of PayloadFragment inheriting WebApiBaseUnit utility methods. */
    export class WebApiPayloadFragment extends WebApiBaseUnitWithEncodesModel {
    }

    /** Subclass of Vocabulary inheriting WebApiBaseUnit utility methods. */
    export class WebApiVocabulary extends WebApiBaseUnit {
      name: model.StrField
      description: model.StrField

      base: model.StrField
      imports: model.domain.VocabularyReference[]
      externals: model.domain.External[]

      withName(name: string): WebApiVocabulary

      withBase(base: string): WebApiVocabulary

      withExternals(externals: model.domain.External[]): WebApiVocabulary

      withImports(vocabularies: model.domain.VocabularyReference[]): WebApiVocabulary

      objectPropertyTerms(): model.domain.ObjectPropertyTerm[]

      datatypePropertyTerms(): model.domain.DatatypePropertyTerm[]

      classTerms(): model.domain.ClassTerm[]
    }
  }

  /** Utility object that hosts initialization logic and
   * classes with syntax-specific methods.
   */
  namespace WebApiParser {

    /**
     * Initializes necessary plugins. Is automatically run when
     * syntax-specific methods are called. Must be called explicitly
     * when constructing API by hand.
     */
    export function init(): Promise<void>

    /** Provides methods for RAML 1.0 processing */
    export class raml10 {

      /** Parses RAML 1.0 content from string or url.
       *
       * @param urlOrContent File url/path or content string to be parsed.
       * @return Parsed WebApi Model.
       */
      static parse(urlOrContent: string): Promise<WebApiBaseUnit>

      /** Parses RAML 1.0 content from string with a custom API Doc location.
       *
       * @param content Content string to be parsed.
       * @param baseUrl Location to assign to a doc parsed from a content string.
       *                References are resolved relative to this location.
       * @return Parsed WebApi Model (future).
       */
      static parse(content: string, baseUrl: string): Promise<WebApiBaseUnit>

      /** Generates file with RAML 1.0 content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @param url Path to the generated file.
       */
      static generateFile(model: WebApiBaseUnit, url: string): Promise<void>

      /** Generates string with RAML 1.0 content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @return Generated string.
       */
      static generateString(model: WebApiBaseUnit): Promise<string>

      /** Validates parsed RAML 1.0 model.
       *
       * @param model Parsed WebApi Model to be validated.
       * @return Validation report.
       */
      static validate(model: WebApiBaseUnit): Promise<client.validate.ValidationReport>

      /** Resolves parsed RAML 1.0 model.
       *
       * Resolution process includes resolving references to all types, libraries, etc.
       *
       * @param model Parsed WebApi Model to be resolved.
       * @return Resolved parsed WebApi Model.
       */
      static resolve(model: WebApiBaseUnit): Promise<WebApiBaseUnit>
    }

    /** Provides methods for RAML 0.8 processing */
    export class raml08 {

      /** Parses RAML 0.8 content from string or url.
       *
       * @param urlOrContent File url/path or content string.
       * @return Parsed WebApi Model.
       */
      static parse(urlOrContent: string): Promise<WebApiBaseUnit>

      /** Parses RAML 0.8 content from string with a custom API Doc location.
       *
       * @param content Content string to be parsed.
       * @param baseUrl Location to assign to a doc parsed from a content string.
       *                References are resolved relative to this location.
       * @return Parsed WebApi Model (future).
       */
      static parse(content: string, baseUrl: string): Promise<WebApiBaseUnit>

      /** Generates file with RAML 0.8 content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @param url Path to the generated file.
       */
      static generateFile(model: WebApiBaseUnit, url: string): Promise<void>

      /** Generates string with RAML 0.8 content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @return Generated string.
       */
      static generateString(model: WebApiBaseUnit): Promise<string>

      /** Validates parsed RAML 0.8 model.
       *
       * @param model Parsed WebApi Model to be validated.
       * @return Validation report.
       */
      static validate(model: WebApiBaseUnit): Promise<client.validate.ValidationReport>

      /** Resolves parsed RAML 0.8 model.
       *
       * Resolution process includes resolving references to all types, libraries, etc.
       *
       * @param model Parsed WebApi Model to be resolved.
       * @return Resolved parsed WebApi Model.
       */
      static resolve(model: WebApiBaseUnit): Promise<WebApiBaseUnit>
    }

    /** Provides methods for OAS 2.0 processing */
    export class oas20 {

      /** Parses OAS 2.0 JSON content from string or url.
       *
       * @param urlOrContent File url/path or content string.
       * @return Parsed WebApi Model.
       */
      static parse(urlOrContent: string): Promise<WebApiBaseUnit>

      /** Parses OAS 2.0 JSON content from string with a custom API Doc location.
       *
       * @param content Content string to be parsed.
       * @param baseUrl Location to assign to a doc parsed from a content string.
       *                References are resolved relative to this location.
       * @return Parsed WebApi Model (future).
       */
      static parse(content: string, baseUrl: string): Promise<WebApiBaseUnit>

      /** Generates file with OAS 2.0 JSON content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @param url Path to the generated file.
       */
      static generateFile(model: WebApiBaseUnit, url: string): Promise<void>

      /** Generates string with OAS 2.0 JSON content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @return Generated string.
       */
      static generateString(model: WebApiBaseUnit): Promise<string>

      /** Validates parsed OAS 2.0 model.
       *
       * @param model Parsed WebApi Model to be validated.
       * @return Validation report.
       */
      static validate(model: WebApiBaseUnit): Promise<client.validate.ValidationReport>

      /** Resolves parsed OAS 2.0 model.
       *
       * Resolution process includes resolving references to all types, libraries, etc.
       *
       * @param model Parsed WebApi Model to be resolved.
       * @return Resolved parsed WebApi Model.
       */
      static resolve(model: WebApiBaseUnit): Promise<WebApiBaseUnit>

      /** Parses OAS 2.0 YAML content from string or url.
       *
       * @param urlOrContent File url/path or content string.
       * @return Parsed WebApi Model.
       */
      static parseYaml(urlOrContent: string): Promise<WebApiBaseUnit>

      /** Parses OAS 2.0 YAML content from string with a custom API Doc location.
       *
       * @param content Content string to be parsed.
       * @param baseUrl Location to assign to a doc parsed from a content string.
       *                References are resolved relative to this location.
       * @return Parsed WebApi Model (future).
       */
      static parseYaml(content: string, baseUrl: string): Promise<WebApiBaseUnit>

      /** Generates file with OAS 2.0 YAML content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @param url Path to the generated file.
       */
      static generateYamlFile(model: WebApiBaseUnit, url: string): Promise<void>

      /** Generates string with OAS 2.0 YAML content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @return Generated string.
       */
      static generateYamlString(model: WebApiBaseUnit): Promise<string>
    }

    /** BETA! Provides methods for OAS 3.0 processing */
    export class oas30 {

      /** BETA! Parses OAS 3.0 JSON content from string or url.
       *
       * @param urlOrContent File url/path or content string.
       * @return Parsed WebApi Model.
       */
      static parse(urlOrContent: string): Promise<WebApiBaseUnit>

      /** BETA! Parses OAS 3.0 JSON content from string with a custom API Doc location.
       *
       * @param content Content string to be parsed.
       * @param baseUrl Location to assign to a doc parsed from a content string.
       *                References are resolved relative to this location.
       * @return Parsed WebApi Model (future).
       */
      static parse(content: string, baseUrl: string): Promise<WebApiBaseUnit>

      /** BETA! Generates file with OAS 3.0 JSON content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @param url Path to the generated file.
       */
      static generateFile(model: WebApiBaseUnit, url: string): Promise<void>

      /** BETA! Generates string with OAS 3.0 JSON content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @return Generated string.
       */
      static generateString(model: WebApiBaseUnit): Promise<string>

      /** BETA! Validates parsed OAS 3.0 model.
       *
       * @param model Parsed WebApi Model to be validated.
       * @return Validation report.
       */
      static validate(model: WebApiBaseUnit): Promise<client.validate.ValidationReport>

      /** BETA! Resolves parsed OAS 3.0 model.
       *
       * Resolution process includes resolving references to all types, libraries, etc.
       *
       * @param model Parsed WebApi Model to be resolved.
       * @return Resolved parsed WebApi Model.
       */
      static resolve(model: WebApiBaseUnit): Promise<WebApiBaseUnit>

      /** BETA! Parses OAS 3.0 YAML content from string or url.
       *
       * @param urlOrContent File url/path or content string.
       * @return Parsed WebApi Model.
       */
      static parseYaml(urlOrContent: string): Promise<WebApiBaseUnit>

      /** BETA! Parses OAS 3.0 YAML content from string with a custom API Doc location.
       *
       * @param content Content string to be parsed.
       * @param baseUrl Location to assign to a doc parsed from a content string.
       *                References are resolved relative to this location.
       * @return Parsed WebApi Model (future).
       */
      static parseYaml(content: string, baseUrl: string): Promise<WebApiBaseUnit>

      /** BETA! Generates file with OAS 3.0 YAML content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @param url Path to the generated file.
       */
      static generateYamlFile(model: WebApiBaseUnit, url: string): Promise<void>

      /** BETA! Generates string with OAS 3.0 YAML content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @return Generated string.
       */
      static generateYamlString(model: WebApiBaseUnit): Promise<string>
    }

    /** Provides methods for AMF Graph processing */
    export class amfGraph {

      /** Parses AMF Graph content from string or url.
       *
       * @param urlOrContent File url/path or content string.
       * @return Parsed WebApi Model.
       */
      static parse(urlOrContent: string): Promise<WebApiBaseUnit>

      /** Parses AMF Graph content from string with a custom API Doc location.
       *
       * @param content Content string to be parsed.
       * @param baseUrl Location to assign to a doc parsed from a content string.
       *                References are resolved relative to this location.
       * @return Parsed WebApi Model (future).
       */
      static parse(content: string, baseUrl: string): Promise<WebApiBaseUnit>

      /** Generates file with AMF Graph content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @param url Path to the generated file.
       */
      static generateFile(model: WebApiBaseUnit, url: string): Promise<void>

      /** Generates string with AMF Graph content.
       *
       * @param model Parsed WebApi Model to generate content from.
       * @return Generated string.
       */
      static generateString(model: WebApiBaseUnit): Promise<string>

      /** Validates parsed AMF Graph model.
       *
       * @param model Parsed WebApi Model to be validated.
       * @return Validation report.
       */
      static validate(model: WebApiBaseUnit): Promise<client.validate.ValidationReport>

      /** Resolves parsed AMF Graph model.
       *
       * Resolution process includes resolving references to all types, libraries, etc.
       *
       * @param model Parsed WebApi Model to be resolved.
       * @return Resolved parsed WebApi Model.
       */
      static resolve(model: WebApiBaseUnit): Promise<WebApiBaseUnit>
    }
  }
}