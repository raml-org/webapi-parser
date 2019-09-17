/**
 * Example of parsing RAML 1.0 with basePath parameter.
 */
const wap = require('webapi-parser').WebApiParser
const path = require('path')

const ramlStr = `
  #%RAML 1.0
  title: API with Types
  /users/{id}:
    get:
      responses:
        200:
          body:
            application/json:
              type: !include cat-schema.json
`

async function main () {
  // Parse RAML 1.0 string with basePath parameter
  console.log('Input:\n', ramlStr)
  const fpath = path.resolve(
    __dirname,
    '../api-specs/includes/')
  const baseUrl = `file://${fpath}`
  const model = await wap.raml10.parse(ramlStr, baseUrl)
  console.log('Model location:\n', model.location)
  const resolved = await wap.raml10.resolve(model)

  // Generate RAML 1.0 string
  const generated = await wap.raml10.generateString(resolved)
  console.log('Generated:\n', generated)
}

main()
