/**
 * Example of converting one and two RAML DataTypes to JSON Schema
 */
const conversion = require('webapi-parser').Conversion

const ramlStr = `
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
        firstName: string
        lastName:  string
        age:
          type: integer
          minimum: 0
          maximum: 99
`

async function main () {
  // Convert single type (defined first)
  const book = await conversion.toJsonSchema(ramlStr, 'Book')
  console.log('Book converted:', book)

  // Convert single type (defined second)
  const user = await conversion.toJsonSchema(ramlStr, 'User')
  console.log('User converted:', user)

  // Try to convert inexisting type
  const inexisting = await conversion.toJsonSchema(ramlStr, 'FooBar')
  console.log('Inexisting type converted:', inexisting)
}

main()
