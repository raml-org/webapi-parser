/**
 * Example of converting one and two RAML DataTypes to JSON Schema
 */
const conversion = require('webapi-parser').Conversion

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
  // Convert single type (defined first)
  const user = await conversion.toJsonSchema(ramlApi, 'User')
  console.log('User from API:', user)

  // Convert single type (defined second)
  const book = await conversion.toJsonSchema(ramlLibrary, 'Book')
  console.log('Book from Library:', book)

  // Convert single type from DataType fragment
  const dt = await conversion.toJsonSchema(ramlDataType, 'AnyName')
  console.log('Single type from DataType fragment:', dt)

  // Try to convert inexisting type
  try {
    const inexisting = await conversion.toJsonSchema(ramlApi, 'FooBar')
  } catch (e) {
    console.log(`Tried to convert inexisting type from API: ${e.toString()}\n`)
  }
}

main()
