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

async function main () {
  console.log('Input:\n', ramlStr)
  const model = await wap.raml10.parse(ramlStr)

  // Modify content
  const age = model.declares[0].properties[2]
  age.range.withMaximum(321)

  const generated = await wap.oas20.generateString(model)
  console.log('Generated:\n', generated)
}

main()
