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
  /users/{id}:
    get:
      responses:
        200:
          body:
            application/json:
              type: User
`

async function processString () {
  const model = await wap.raml10.parseString(ramlStr)
  const resolved = await wap.raml10.resolve(model)
  const report = await wap.raml10.validate(model)
  console.log('Validation errors:\n', report.results)

  const age = model.findById(
    'http://a.ml/amf/default_document#/declarations/types/User/property/age')
  age.range.withMinimum(18)
  age.range.withMaximum(120)

  const generated = await wap.raml10.generateString(model)
  console.log('Generated:\n', generated)
}

processString()
