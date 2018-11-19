const wap = require('webapi-parser').WebApiParser
const path = require('path')

async function main () {
  const inPath = path.join(__dirname, '../api-specs/oas/api-with-types.json')
  const model = await wap.oas20.parseFile(`file://${inPath}`)
  const resolved = await wap.oas20.resolve(model)

  const report = await wap.oas20.validate(resolved)
  console.log('Validation errors:\n', report.results)

  // Modify content
  const age = resolved.findById(
    `file://${inPath}#/declarations/types/User/property/age`)
  age.range.withMinimum(18)
  age.range.withMaximum(120)

  const outPath = path.join(__dirname, './generated.json')
  await wap.oas20.generateFile(resolved, `file://${outPath}`)
}

main()
