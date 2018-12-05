const wap = require('webapi-parser').WebApiParser

const oas20Str = `
  openapi: '2.0'
  info:
    title: API with Types
    version: ''
  definitions:
    User:
      type: object
      properties:
        firstName:
          type: string
        lastName:
          type: string
        age:
          minimum: 0
          maximum: 99
          type: integer
      required:
      - firstName
      - lastName
      - age
  paths:
    "/users/{id}":
      get:
        operationId: GET_users-id
        produces:
        - application/json
        responses:
          '200':
            description: ''
            schema:
              "$ref": "#/definitions/User"
      parameters:
      - type: string
        in: path
        name: id
        required: true
`

async function main () {
  console.log('Input:\n', oas20Str)
  const model = await wap.oas20.parseYaml(oas20Str)

  // Modify content
  const age = model.declares[0].properties[2]
  age.range.withMinimum(18)
  age.range.withMaximum(120)

  const generated = await wap.oas20.generateString(model)
  console.log('Generated:\n', generated)
}

main()
