/**
 * Example of parsing and generating RAML 1.0 strings.
 */
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
  // Parse RAML 1.0 string
  console.log('Input:\n', ramlStr)
  const model = await wap.raml10.parse(ramlStr)

  // Get User.age property
  const age = model.declares[0].properties[2]

  // Set age maximum to 120
  age.range.withMaximum(120)

  // Get first endpoint POST method
  const post = model.encodes.endPoints[0].withOperation('post')

  // Add 200 response to POST method
  const resp = post.withResponse('200')

  // Set 200 response description
  resp.withDescription('POST 200 response')

  // Generate RAML 1.0 string
  const generated = await wap.raml10.generateString(model)
  console.log('Generated:\n', generated)
}

main()
