/**
 * Example of parsing OAS 2.0 YAML and generating OAS 2.0 YAML strings.
 */
const wap = require('webapi-parser').WebApiParser

const oas20Str = `
  swagger: '2.0'
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
  // Parse OAS 2.0 YAML string
  console.log('Input:\n', oas20Str)
  const model = await wap.oas20.parseYaml(oas20Str)

  // Get User.age property
  const age = model.declares[0].properties[2]

  // Set age minimum to 18
  age.range.withMinimum(18)

  // Set age maximum to 120
  age.range.withMaximum(120)

  // Remove first endpoint 'parameters'
  model.encodes.endPoints[0].withParameters([])

  // Generate OAS 2.0 YAML string
  const generated = await wap.oas20.generateYamlString(model)
  console.log('Generated:\n', generated)
}

main()
