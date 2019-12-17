/**
 * Example of parsing OAS 3.0 YAML and generating OAS 3.0 YAML strings.
 */
const wap = require('webapi-parser').WebApiParser

const oas30Str = `
  openapi: 3.0.0
  info:
    title: API with Types
    version: ""
  paths:
    "/users/{id}":
      get:
        operationId: GET_users-id
        responses:
          "200":
            description: ""
            content:
              application/json:
                schema:
                  $ref: "#/components/schemas/User"
      parameters:
        - in: path
          name: id
          required: true
          schema:
            type: string
  components:
    schemas:
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
`

async function main () {
  // Parse OAS 3.0 YAML string
  console.log('Input:\n', oas30Str)
  const model = await wap.oas30.parseYaml(oas30Str)

  // Get User.age property
  const age = model.declares[0].properties[2]

  // Set age minimum to 18
  age.range.withMinimum(18)

  // Set age maximum to 120
  age.range.withMaximum(120)

  // Remove first endpoint 'parameters'
  model.encodes.endPoints[0].withParameters([])

  // Generate OAS 3.0 YAML string
  const generated = await wap.oas30.generateYamlString(model)
  console.log('Generated:\n', generated)
}

main()
