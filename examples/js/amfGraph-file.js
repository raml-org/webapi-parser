const wap = require('webapi-parser').WebApiParser
const path = require('path')

async function main () {
  const inPath = path.join(__dirname, '../api-specs/amf-graph/api-with-types.json')

  const model = await wap.amfGraph.parseFile(`file://${inPath}`)
  const resolved = await wap.amfGraph.resolve(model)
  const report = await wap.amfGraph.validate(model)
  console.log('Validation errors:\n', report.results)

  // Modify content
  const age = model.findById(
    `http://a.ml/amf/default_document#/declarations/types/User/property/age`)
  age.range.withMinimum(18)
  age.range.withMaximum(120)

  const outPath = path.join(__dirname, './generated.json')
  await wap.amfGraph.generateFile(model, `file://${outPath}`)
}

main()
