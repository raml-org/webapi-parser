const wap = require('webapi-parser').WebApiParser

const ramlStr = `
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
  const model = await wap.raml10.parseString(ramlStr)
  // const resolved = await wap.raml10.resolve(model)
  const report = await wap.raml10.validate(model)
  console.log('Validation errors:\n', report.results)
}

main()
