import {model, client} from 'amf-client-js';

declare module "webapi-parser" {
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