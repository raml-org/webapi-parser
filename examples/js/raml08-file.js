const wap = require('webapi-parser').WebApiParser
const path = require('path')

async function main () {
  const inPath = path.join(__dirname, '../api-specs/raml/magic-api.raml')
  const model = await wap.raml08.parseFile(`file://${inPath}`)
  const resolved = await wap.raml08.resolve(model)

  const report = await wap.raml08.validate(resolved)
  console.log('Validation errors:\n', report.results)

  // Modify content
  const perPage = resolved.findById(
    `file://${inPath}#/web-api/end-points/%2Fitems/get/request/parameter/per_page`)
  perPage.schema.withMaximum(100)

  const outPath = path.join(__dirname, './generated.raml')
  await wap.raml08.generateFile(resolved, `file://${outPath}`)
}

main()
