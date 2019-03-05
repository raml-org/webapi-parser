/**
 * Example of parsing and generating RAML 0.8 strings.
 */
const wap = require('webapi-parser').WebApiParser

const ramlStr = `
  #%RAML 0.8
  title: Magic API
  version: v3
  baseUri: https://magic.api.com
  /items:
    get:
      description: Get a list of magic items
      queryParameters:
        page:
          type: integer
        per_page:
          type: integer
`

async function main () {
  // Parse RAML 0.8 string
  const model = await wap.raml08.parse(ramlStr)

  // Resolve parsed AMF Model (optional)
  const resolved = await wap.raml08.resolve(model)

  // Get first endpoint GET method request query parameter 'per_page'
  const perPage = resolved.encodes.endPoints[0].operations[0].request.queryParameters[1]

  // Set per_page parameter maximum to 100
  perPage.schema.withMaximum(100)

  // Set API description
  resolved.encodes.withDescription('My new magic api')

  // Generate RAML 0.8 string of the resolved and updated model
  const generated = await wap.raml08.generateString(model)
  console.log('Generated:\n', generated)
}

main()
