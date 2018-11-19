const wap = require('webapi-parser').WebApiParser
const path = require('path')

async function main () {
  const inPath = path.join(__dirname, '../api-specs/raml/api-with-types.raml')
  const model = await wap.raml10.parseFile(`file://${inPath}`)
  const resolved = await wap.raml10.resolve(model)

  const report = await wap.raml10.validate(resolved)
  console.log('Validation errors:\n', report.results)

  // Modify content
  const age = resolved.findById(
    `file://${inPath}#/declarations/types/User/property/age`)
  age.range.withMinimum(18)
  age.range.withMaximum(120)

  const outPath = path.join(__dirname, './generated.raml')
  await wap.raml10.generateFile(resolved, `file://${outPath}`)
}

main()
