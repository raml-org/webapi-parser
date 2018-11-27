const wap = require('webapi-parser').WebApiParser
const path = require('path')

async function main () {
  const inPath = path.join(__dirname, '../api-specs/raml/magic-api.raml')
  const model = await wap.raml08.parse(`file://${inPath}`)
  const resolved = await wap.raml08.resolve(model)

  const report = await wap.raml08.validate(resolved)
  console.log('Validation errors:\n', report.results)

  // Modify content
  const perPage = resolved.encodes.endPoints[0].operations[0].request.queryParameters[1]
  perPage.schema.withMaximum(100)

  const outPath = path.join(__dirname, './generated.raml')
  await wap.raml08.generateFile(resolved, `file://${outPath}`)
}

main()
