/**
 * Example of RAML 1.0 string parsing and validation.
 */
const wap = require('webapi-parser').WebApiParser

const invalidRaml = `
  #%RAML 1.0
  title: API with Types
  types:
    User:
      type: object
      properties:
        firstName: string
        lastName:  string
        age:
          type: integer
          minimum: 0
          maximum: 99
          example: 123123
      example:
        firstName: John
        lastName: 123
        age: 127
        foo: bar
  /users/{id}:
    get:
      responses:
        200:
          body:
            application/json:
              type: User
              example:
                firstName: John
                lastName: 123
                age: 127
                foo: bar
`

async function main () {
  // Parse invalid RAML 1.0 string
  const model = await wap.raml10.parse(invalidRaml)

  // Validate parser model and get validation results
  const report = await wap.raml10.validate(model)

  // Log validation results
  console.log('Validation errors:\n', report.results)
}

main()
