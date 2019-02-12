/**
 * Example of converting one and two RAML DataTypes to JSON Schema
 */
const conversion = require('webapi-parser').Conversion
const path = require('path')

const ramlApi = `
  #%RAML 1.0
  title: API with Types
  types:
    Book:
      type: object
      properties:
        title: string
        author: string
    User:
      type: object
      properties:
        username: string
`

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
  // Convert single type from RAML API string
  const user = await conversion.toJsonSchema(ramlApi, 'User')
  console.log('User from API string:', user)

  // Convert single type from RAML Library string
  const book = await conversion.toJsonSchema(ramlLibrary, 'Book')
  console.log('Book from Library string:', book)

  // Convert single type from DataType fragment string
  const dt = await conversion.toJsonSchema(ramlDataType, 'AnyName')
  console.log('Single type from DataType fragment string:', dt)

  // Try to convert inexisting type from RAML API string
  try {
    const inexisting = await conversion.toJsonSchema(ramlApi, 'FooBar')
  } catch (e) {
    console.log(`Tried to convert inexisting type from API: ${e.toString()}\n`)
  }

  // Convert single type from RAML Library file with `uses`
  const inPath = path.join(__dirname, '../api-specs/raml/lib-uses-lib.raml')
  const book2 = await conversion.toJsonSchema(`file://${inPath}`, 'Book')
  console.log('Book from API file:', book2)
}

main()
