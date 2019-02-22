/**
 * Example of converting JSON Schema to RAML Data Type using AMF Model
 */
const wap = require('webapi-parser').WebApiParser
const path = require('path')


async function main () {
  // Parse an API document string
  const inPath = path.join(__dirname, '../api-specs/oas/pet-store-api.json')
  const docPath = `file://${inPath}`
  const model = await wap.oas20.parse(docPath)

  // Convert type from "definitions".
  // Type can be picked using utility functions.
  console.log(
    'RAML Data Type from definitions using util:\n',
    model.findById(`${docPath}#/declarations/types/Pet`).toRamlDatatype)

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
