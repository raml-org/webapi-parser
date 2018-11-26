import * as amf from 'amf-client-js';

declare module "webapi-parser" {

    namespace WebApiParser {

        export class raml10 {
            static parseFile(url: string): Promise<amf.model.document.BaseUnit>

            static parseString(content: string): Promise<amf.model.document.BaseUnit>

            static generateFile(model: amf.model.document.BaseUnit, url: string): Promise<void>

            static generateString(model: amf.model.document.BaseUnit): Promise<string>

            static validate(model: amf.model.document.BaseUnit): Promise<amf.validate.ValidationReport>

            static resolve(model: amf.model.document.BaseUnit): Promise<amf.model.document.BaseUnit>
        }

        export class raml08 {
            static parseFile(url: string): Promise<amf.model.document.BaseUnit>

            static parseString(content: string): Promise<amf.model.document.BaseUnit>

            static generateFile(model: amf.model.document.BaseUnit, url: string): Promise<void>

            static generateString(model: amf.model.document.BaseUnit): Promise<string>

            static validate(model: amf.model.document.BaseUnit): Promise<amf.validate.ValidationReport>

            static resolve(model: amf.model.document.BaseUnit): Promise<amf.model.document.BaseUnit>
        }

        export class oas20 {
            static parseFile(url: string): Promise<amf.model.document.BaseUnit>

            static parseString(content: string): Promise<amf.model.document.BaseUnit>

            static generateFile(model: amf.model.document.BaseUnit, url: string): Promise<void>

            static generateString(model: amf.model.document.BaseUnit): Promise<string>

            static validate(model: amf.model.document.BaseUnit): Promise<amf.validate.ValidationReport>

            static resolve(model: amf.model.document.BaseUnit): Promise<amf.model.document.BaseUnit>

            static parseYamlFile(url: string): Promise<amf.model.document.BaseUnit>

            static parseYamlString(content: string): Promise<amf.model.document.BaseUnit>
        }

        export class amfGraph {
            static parseFile(url: string): Promise<amf.model.document.BaseUnit>

            static parseString(content: string): Promise<amf.model.document.BaseUnit>

            static generateFile(model: amf.model.document.BaseUnit, url: string): Promise<void>

            static generateString(model: amf.model.document.BaseUnit): Promise<string>

            static validate(model: amf.model.document.BaseUnit): Promise<amf.validate.ValidationReport>

            static resolve(model: amf.model.document.BaseUnit): Promise<amf.model.document.BaseUnit>
        }
    }
}