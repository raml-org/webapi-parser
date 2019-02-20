/**
 * Example of converting JSON Schema to RAML Data Type using AMF Model
 */
const wap = require('webapi-parser').WebApiParser
const path = require('path')

const schema = `
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
          "summary": "Info for a specific pet",
          "operationId": "showPetById",
          "tags": ["pets"],
          "parameters": [{
            "name": "petId",
            "in": "path",
            "required": true,
            "description": "The id of the pet to retrieve",
            "type": "string"
          }],
          "responses": {
            "200": {
              "description": "Expected response to a valid request",
              "schema": {"$ref": "#/definitions/Pet"}
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
      }
    }
  }
`

async function main () {
  // Parse an API document string
  const model = await wap.oas20.parse(schema)

  // Convert type from "definitions".
  // Type can be picked using utility functions
  console.log(
    'RAML Data type from definitions using util:\n',
    model.findById(
      'http://a.ml/amf/default_document#/declarations/types/Pet'
    ).toRamlDatatype)

  // Type can also be picked by index.
  console.log(
    'RAML Data type from definitions by index:\n',
    model.declares[0].toRamlDatatype)
}

main()
