/**
 * Example of parsing and generating RAML 0.8 files.
 */
const wap = require('webapi-parser').WebApiParser
const path = require('path')

async function main () {
  // Parse RAML 0.8 file
  const inPath = path.join(__dirname, '../api-specs/raml/magic-api.raml')
  const model = await wap.raml08.parse(`file://${inPath}`)
  const resolved = await wap.raml08.resolve(model)

  // Get first endpoint GET method request query parameter 'per_page'
  const perPage = resolved.encodes.endPoints[0].operations[0].request.queryParameters[1]

  // Set per_page parameter maximum to 100
  perPage.schema.withMaximum(100)

  // Add new endpoint with path /documents
  const endpoint = resolved.encodes.withEndPoint('/documents')

  // Add method GET /documents
  endpoint.withOperation('get')

  // Set GET /documents name to 'getdocs'
  endpoint.operations[0].withName('getdocs')

  // Generate RAML 0.8 string
  const outPath = path.join(__dirname, './generated.raml')
  console.log('Generating file to:', outPath)
  await wap.raml08.generateFile(resolved, `file://${outPath}`)
}

main()
