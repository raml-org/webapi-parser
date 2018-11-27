const wap = require('webapi-parser').WebApiParser
const path = require('path')

async function main () {
  const inPath = path.join(__dirname, '../api-specs/raml/api-with-types.raml')
  const model = await wap.raml10.parse(`file://${inPath}`)

  const report = await wap.raml10.validate(model)
  console.log('Validation errors:\n', report.results)

  // Modify content
  const age = model.declares[0].properties[2]
  age.range.withMinimum(18)
  age.range.withMaximum(120)

  const outPath = path.join(__dirname, './generated.raml')
  await wap.raml10.generateFile(model, `file://${outPath}`)
}

main()
