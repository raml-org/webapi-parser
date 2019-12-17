/**
 * Example of parsing and generating OAS 3.0 JSON strings.
 */
const wap = require('webapi-parser').WebApiParser

const oas30Str = `
  {
    "openapi": "3.0.0",
    "info": {
      "title": "API with Types",
      "version": ""
    },
    "paths": {
      "/users/{id}": {
        "get": {
          "operationId": "GET_users-id",
          "responses": {
            "200": {
              "description": "",
              "content": {
                "application/json": {
                  "schema": {
                    "$ref": "#/components/schemas/User"
                  }
                }
              }
            }
          }
        },
        "parameters": [
          {
            "in": "path",
            "name": "id",
            "required": true,
            "schema": {
              "type": "string"
            }
          }
        ]
      }
    },
    "components": {
      "schemas": {
        "User": {
          "type": "object",
          "required": [
            "firstName",
            "lastName",
            "age"
          ],
          "properties": {
            "firstName": {
              "type": "string"
            },
            "lastName": {
              "type": "string"
            },
            "age": {
              "minimum": 0,
              "maximum": 99,
              "type": "integer"
            }
          }
        }
      }
    }
  }
`

async function main () {
  // Parse OAS 3.0 JSON string
  console.log('Input:\n', oas30Str)
  const model = await wap.oas30.parse(oas30Str)

  // Get User.age property
  const age = model.declares[0].properties[2]

  // Set age minimum to 18
  age.range.withMinimum(18)

  // Set age maximum to 130
  age.range.withMaximum(130)

  // Set API version to 5.0.3
  model.encodes.withVersion('5.0.3')

  // Generate OAS 3.0 JSON string
  const generated = await wap.oas30.generateString(model)
  console.log('Generated:\n', generated)
}

main()
