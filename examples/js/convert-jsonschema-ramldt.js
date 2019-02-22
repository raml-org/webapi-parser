/**
 * Example of converting JSON Schema to RAML Data Type using AMF Model
 */
const wap = require('webapi-parser').WebApiParser

const oasDocument = `
  {
    "swagger": "2.0",
    "info": {
      "version": "1.0.0",
      "title": "Swagger Petstore",
      "license": {"name": "MIT"}
    },
    "host": "petstore.swagger.io",
    "basePath": "/v1",
    "schemes": ["http"],
    "consumes": ["application/json"],
    "produces": ["application/json"],
    "paths": {
      "/pets/{petId}": {
        "get": {
          "responses": {
            "200": {
              "description": "Expected response to a valid request",
              "schema": {"$ref": "#/definitions/Pet"}
            }
          }
        }
      },
      "/pets/{petId}/owner": {
        "get": {
          "responses": {
            "200": {
              "schema": {"$ref": "#/definitions/Owner"}
            }
          }
        }
      }
    },
    "definitions": {
      "Pet": {
        "required": ["id", "name"],
        "properties": {
          "id": {"type": "integer", "format": "int64"},
          "name": {"type": "string"},
          "tag": {"type": "string"}
        }
      },
      "Owner": {
        "type": "object",
        "properties": {
          "name": {"type": "string"},
          "pet": {"$ref": "#/definitions/Pet"}
        }
      }
    }
  }
`

async function main () {
  // Parse an API document string
  const model = await wap.oas20.parse(oasDocument)

  // Convert type from "definitions".
  // Type can be picked using utility functions.
  console.log(
    'RAML Data Type from definitions using util:\n',
    model.findById(
      'http://a.ml/amf/default_document#/declarations/types/Pet'
    ).toRamlDatatype)

  // Type can also be picked by index.
  console.log(
    'RAML Data Type from definitions by index:\n',
    model.declares[0].toRamlDatatype)

  // To properly convert type with references, model needs to be
  // resolved first.
  const resolved = await wap.oas20.resolve(model)
  console.log(
    'RAML Data Type (with references) from resolved document:\n',
    resolved
      .encodes
      .endPoints[1]
      .operations[0]
      .responses[0]
      .payloads[0]
      .schema.toRamlDatatype)
}

main()
