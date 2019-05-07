/**
 * Example of translating a RAML DataType to a JSON Schema using WebApi Model
 */
const wap = require('webapi-parser').WebApiParser
const path = require('path')

const ramlLibrary = `
  #%RAML 1.0 Library
  types:
    Book:
      type: object
      properties:
        title: string
        author: string
`

const ramlDataType = `
  #%RAML 1.0 DataType
  type: object
  properties:
    title: string
    author: string
`

async function main () {
  // Parsing a RAML 1.0 document
  const inPath = path.join(__dirname, '../api-specs/raml/api-with-types.raml')
  const docPath = `file://${inPath}`
  const apiModel = await wap.raml10.parse(docPath)
  // Type can be accessed using utility function `getDeclarationByName()`
  console.log(
    'JSON from API root using util:\n',
    apiModel.getDeclarationByName('User').toJsonSchema)
  // Type can also be accessed by index
  console.log(
    'JSON from API root by index:\n', apiModel.declares[0].toJsonSchema)
  // To convert type referenced in response payloads, model needs to be resolved first
  const resApiModel = await wap.raml10.resolve(apiModel)
  // Note that [0] below corresponds to the first payload of the first response of the first method, etc..
  console.log(
    'JSON from resolved API:\n',
    resApiModel.encodes.endPoints[0].operations[0].responses[0].payloads[0].schema.toJsonSchema)

  // Parsing a RAML 1.0 Library string
  const libModel = await wap.raml10.parse(ramlLibrary)
  // Type can be selected using the utility function `getDeclarationByName()`
  console.log(
    'JSON from Library root using util:\n',
    libModel.getDeclarationByName('Book').toJsonSchema)
  // Type can also be selected by index
  console.log(
    'JSON from Library root by index:\n', libModel.declares[0].toJsonSchema)

  // Parsing a RAML 1.0 DataType string
  const dtModel = await wap.raml10.parse(ramlDataType)
  // Type in DataType is defined under `.encodes`
  console.log(
    'JSON from DataType:\n', dtModel.encodes.toJsonSchema)
}

main()
