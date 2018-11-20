const wap = require('webapi-parser').WebApiParser
const path = require('path')
const fs = require('fs')

async function main () {
  const inPath = path.join(__dirname, '../api-specs/amf-graph/api-with-types.json')
  const graphStr = JSON.stringify(JSON.parse(fs.readFileSync(inPath)))

  const model = await wap.amfGraph.parseString(graphStr)
  const report = await wap.amfGraph.validate(model)
  console.log('Validation errors:\n', report.results)

  // Modify content
  const age = model.declares[0].properties[2]
  age.range.withMinimum(18)
  age.range.withMaximum(120)

  const generated = await wap.amfGraph.generateString(model)
  console.log('Generated:\n', generated)
}

main()
