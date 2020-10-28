/**
 * Example of translating a JSON Schema to a RAML DataType using WebApi Model
 */
const wap = require('webapi-parser').WebApiParser
const path = require('path')

const schema = `
  {
    "$schema": "http://json-schema.org/draft-04/schema",
    "type": "object",
    "required": ["firstName", "lastName", "age"],
    "properties": {
      "firstName": {"type": "string"},
      "lastName": {"type": "string"},
      "age": {"type": "integer", "minimum": 0, "maximum": 99}
    }
  }
`

async function main () {
  const parsedSchema = {
    openapi: '2.0',
    definitions: {
      User: JSON.parse(schema)
    }
  }

  // Parsing an OAS 2.0 string
  const model = await wap.oas20.parse(JSON.stringify(parsedSchema))

  // Type can be accessed using utility function `model.getDeclarationByName()`
  console.log(
    'RAML Data Type from definitions using util:\n',
    model.getDeclarationByName('User').toRamlDatatype())

  // Type can also be accessed by index
  console.log(
    'RAML Data Type from definitions by index:\n',
    model.declares[0].toRamlDatatype())
}

main()
