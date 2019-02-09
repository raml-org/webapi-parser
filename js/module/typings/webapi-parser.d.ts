import {model, client} from 'amf-client-js';

declare module "webapi-parser" {

    /** Provides RAML DT <> JSON Schema conversion utils */
    export class Conversion {
        /** Converts type from RAML 1.0 input to JSON Schema.
         *  Supported input docs are: RAML API spec, RAML Library, RAML DataType.
         *
         * @param ramlInp RAML 1.0 file url/path or content string.
         * @param typeName Name of type to be converted.
         * @return Converted type as JSON Schema string.
         */
        static toJsonSchema(ramlInp: string, typeName: string): Promise<string>
    }

    namespace WebApiParser {

        export class raml10 {
            static parse(urlOrContent: string): Promise<model.document.BaseUnit>

            static generateFile(model: model.document.BaseUnit, url: string): Promise<void>

            static generateString(model: model.document.BaseUnit): Promise<string>

            static validate(model: model.document.BaseUnit): Promise<client.validate.ValidationReport>

            static resolve(model: model.document.BaseUnit): Promise<model.document.BaseUnit>
        }

        export class raml08 {
            static parse(urlOrContent: string): Promise<model.document.BaseUnit>

            static generateFile(model: model.document.BaseUnit, url: string): Promise<void>

            static generateString(model: model.document.BaseUnit): Promise<string>

            static validate(model: model.document.BaseUnit): Promise<client.validate.ValidationReport>

            static resolve(model: model.document.BaseUnit): Promise<model.document.BaseUnit>
        }

        export class oas20 {
            static parse(urlOrContent: string): Promise<model.document.BaseUnit>

            static generateFile(model: model.document.BaseUnit, url: string): Promise<void>

            static generateString(model: model.document.BaseUnit): Promise<string>

            static validate(model: model.document.BaseUnit): Promise<client.validate.ValidationReport>

            static resolve(model: model.document.BaseUnit): Promise<model.document.BaseUnit>

            static parseYaml(urlOrContent: string): Promise<model.document.BaseUnit>
        }

        export class amfGraph {
            static parse(urlOrContent: string): Promise<model.document.BaseUnit>

            static generateFile(model: model.document.BaseUnit, url: string): Promise<void>

            static generateString(model: model.document.BaseUnit): Promise<string>

            static validate(model: model.document.BaseUnit): Promise<client.validate.ValidationReport>

            static resolve(model: model.document.BaseUnit): Promise<model.document.BaseUnit>
        }
    }
}