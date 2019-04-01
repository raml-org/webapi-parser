import {model, client} from 'amf-client-js';

declare module "webapi-parser" {

  /** Custom BaseUnit subclass which implements utility methods. */
  abstract class WebApiBaseUnit extends model.document.BaseUnit {

    /** Gets declaration by name.
     *
     * @param name String name of declaration to look for.
     * @return Found declaration as NodeShape.
     */
    getDeclarationByName(name: string): model.domain.NodeShape
  }

  namespace webapi {
    /** Subclass of Document inheriting WebApiBaseUnit utility methods. */
    export class WebApiDocument extends WebApiBaseUnit {
    }

    /** Subclass of Module inheriting WebApiBaseUnit utility methods. */
    export class WebApiModule extends WebApiBaseUnit {
    }

    /** Subclass of ExternalFragment inheriting WebApiBaseUnit utility methods. */
    export class WebApiExternalFragment extends WebApiBaseUnit {
    }

    /** Subclass of Extension inheriting WebApiBaseUnit utility methods. */
    export class WebApiExtension extends WebApiBaseUnit {
    }

    /** Subclass of Overlay inheriting WebApiBaseUnit utility methods. */
    export class WebApiOverlay extends WebApiBaseUnit {
    }

    /** Subclass of DocumentationItem inheriting WebApiBaseUnit utility methods. */
    export class WebApiDocumentationItem extends WebApiBaseUnit {
    }

    /** Subclass of DataType inheriting WebApiBaseUnit utility methods. */
    export class WebApiDataType extends WebApiBaseUnit {
    }

    /** Subclass of NamedExample inheriting WebApiBaseUnit utility methods. */
    export class WebApiNamedExample extends WebApiBaseUnit {
    }

    /** Subclass of ResourceTypeFragment inheriting WebApiBaseUnit utility methods. */
    export class WebApiResourceTypeFragment extends WebApiBaseUnit {
    }

    /** Subclass of TraitFragment inheriting WebApiBaseUnit utility methods. */
    export class WebApiTraitFragment extends WebApiBaseUnit {
    }

    /** Subclass of AnnotationTypeDeclaration inheriting WebApiBaseUnit utility methods. */
    export class WebApiAnnotationTypeDeclaration extends WebApiBaseUnit {
    }

    /** Subclass of SecuritySchemeFragment inheriting WebApiBaseUnit utility methods. */
    export class WebApiSecuritySchemeFragment extends WebApiBaseUnit {
    }

    /** Subclass of PayloadFragment inheriting WebApiBaseUnit utility methods. */
    export class WebApiPayloadFragment extends WebApiBaseUnit {
    }

    /** Subclass of Vocabulary inheriting WebApiBaseUnit utility methods. */
    export class WebApiVocabulary extends WebApiBaseUnit {
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
       * @return Parsed AMF Model.
       */
      static parse(urlOrContent: string): Promise<WebApiBaseUnit>

      /** Generates file with RAML 1.0 content.
       *
       * @param model Parsed AMF Model to generate content from.
       * @param url Path to the generated file.
       */
      static generateFile(model: WebApiBaseUnit, url: string): Promise<void>

      /** Generates string with RAML 1.0 content.
       *
       * @param model Parsed AMF Model to generate content from.
       * @return Generated string.
       */
      static generateString(model: WebApiBaseUnit): Promise<string>

      /** Validates parsed RAML 1.0 model.
       *
       * @param model Parsed AMF Model to be validated.
       * @return Validation report.
       */
      static validate(model: WebApiBaseUnit): Promise<client.validate.ValidationReport>

      /** Resolves parsed RAML 1.0 model.
       *
       * Resolution process includes resolving references to all types, libraries, etc.
       *
       * @param model Parsed AMF Model to be resolved.
       * @return Resolved parsed AMF model.
       */
      static resolve(model: WebApiBaseUnit): Promise<WebApiBaseUnit>
    }

    /** Provides methods for RAML 0.8 processing */
    export class raml08 {

      /** Parses RAML 0.8 content from string or url.
       *
       * @param urlOrContent File url/path or content string.
       * @return Parsed AMF Model.
       */
      static parse(urlOrContent: string): Promise<WebApiBaseUnit>

      /** Generates file with RAML 0.8 content.
       *
       * @param model Parsed AMF Model to generate content from.
       * @param url Path to the generated file.
       */
      static generateFile(model: WebApiBaseUnit, url: string): Promise<void>

      /** Generates string with RAML 0.8 content.
       *
       * @param model Parsed AMF Model to generate content from.
       * @return Generated string.
       */
      static generateString(model: WebApiBaseUnit): Promise<string>

      /** Validates parsed RAML 0.8 model.
       *
       * @param model Parsed AMF Model to be validated.
       * @return Validation report.
       */
      static validate(model: WebApiBaseUnit): Promise<client.validate.ValidationReport>

      /** Resolves parsed RAML 0.8 model.
       *
       * Resolution process includes resolving references to all types, libraries, etc.
       *
       * @param model Parsed AMF Model to be resolved.
       * @return Resolved parsed AMF model.
       */
      static resolve(model: WebApiBaseUnit): Promise<WebApiBaseUnit>
    }

    /** Provides methods for OAS 2.0 processing */
    export class oas20 {

      /** Parses OAS 2.0 JSON content from string or url.
       *
       * @param urlOrContent File url/path or content string.
       * @return Parsed AMF Model.
       */
      static parse(urlOrContent: string): Promise<WebApiBaseUnit>

      /** Generates file with OAS 2.0 JSON content.
       *
       * @param model Parsed AMF Model to generate content from.
       * @param url Path to the generated file.
       */
      static generateFile(model: WebApiBaseUnit, url: string): Promise<void>

      /** Generates string with OAS 2.0 JSON content.
       *
       * @param model Parsed AMF Model to generate content from.
       * @return Generated string.
       */
      static generateString(model: WebApiBaseUnit): Promise<string>

      /** Validates parsed OAS 2.0 model.
       *
       * @param model Parsed AMF Model to be validated.
       * @return Validation report.
       */
      static validate(model: WebApiBaseUnit): Promise<client.validate.ValidationReport>

      /** Resolves parsed OAS 2.0 model.
       *
       * Resolution process includes resolving references to all types, libraries, etc.
       *
       * @param model Parsed AMF Model to be resolved.
       * @return Resolved parsed AMF model.
       */
      static resolve(model: WebApiBaseUnit): Promise<WebApiBaseUnit>

      /** Parses OAS 2.0 YAML content from string or url.
       *
       * @param urlOrContent File url/path or content string.
       * @return Parsed AMF Model.
       */
      static parseYaml(urlOrContent: string): Promise<WebApiBaseUnit>
    }

    /** Provides methods for AMF Graph processing */
    export class amfGraph {

      /** Parses AMF Graph content from string or url.
       *
       * @param urlOrContent File url/path or content string.
       * @return Parsed AMF Model.
       */
      static parse(urlOrContent: string): Promise<WebApiBaseUnit>

      /** Generates file with AMF Graph content.
       *
       * @param model Parsed AMF Model to generate content from.
       * @param url Path to the generated file.
       */
      static generateFile(model: WebApiBaseUnit, url: string): Promise<void>

      /** Generates string with AMF Graph content.
       *
       * @param model Parsed AMF Model to generate content from.
       * @return Generated string.
       */
      static generateString(model: WebApiBaseUnit): Promise<string>

      /** Validates parsed AMF Graph model.
       *
       * @param model Parsed AMF Model to be validated.
       * @return Validation report.
       */
      static validate(model: WebApiBaseUnit): Promise<client.validate.ValidationReport>

      /** Resolves parsed AMF Graph model.
       *
       * Resolution process includes resolving references to all types, libraries, etc.
       *
       * @param model Parsed AMF Model to be resolved.
       * @return Resolved parsed AMF model.
       */
      static resolve(model: WebApiBaseUnit): Promise<WebApiBaseUnit>
    }
  }
}