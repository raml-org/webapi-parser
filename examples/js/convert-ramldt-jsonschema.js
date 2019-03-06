/**
 * Example of converting RAML DataTypes to JSON Schema using AMF Model
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
  // API DOCUMENT
  // Parse an API document
  const inPath = path.join(__dirname, '../api-specs/raml/api-with-types.raml')
  const docPath = `file://${inPath}`
  const apiModel = await wap.raml10.parse(docPath)

  // Convert type from root. Type can be picked using utility functions
  console.log(
    'JSON from API root using util:\n',
    apiModel.findById(`${docPath}#/declarations/types/User`).toJsonSchema)
  // Type can also be picked by index.
  console.log(
    'JSON from API root by index:\n', apiModel.declares[0].toJsonSchema)

  // To convert type referenced in response payload, model needs to be
  // resolved first.
  // Note we are picking first payload of first response of first
  // method etc here.
  const resApiModel = await wap.raml10.resolve(apiModel)
  console.log(
    'JSON from resolved API:\n',
    resApiModel.encodes.endPoints[0].operations[0].responses[0].payloads[0].schema.toJsonSchema)

  // LIBRARY
  // Parse a Library string
  const libModel = await wap.raml10.parse(ramlLibrary)

  // Convert type from root. Type can be picked using utility functions
  console.log(
    'JSON from Library root using util:\n',
    libModel.findById('http://a.ml/amf/default_document#/declarations/types/Book'
      ).toJsonSchema)
  // Type can also be picked by index.
  console.log(
    'JSON from Library root by index:\n', libModel.declares[0].toJsonSchema)

  // DATATYPE
  // Parse a DataType string
  const dtModel = await wap.raml10.parse(ramlDataType)

  // Type in DataType is defined under `.encodes`
  console.log(
    'JSON from DataType:\n', dtModel.encodes.toJsonSchema)
}

main()
