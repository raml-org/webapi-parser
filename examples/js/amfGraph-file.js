/**
 * Example of parsing and generating AMF Graph files.
 */
const wap = require('webapi-parser').WebApiParser
const path = require('path')

async function main () {
  // Parse JSON API file
  const inPath = path.join(__dirname, '../api-specs/amf-graph/api-with-types.json')
  const model = await wap.amfGraph.parse(`file://${inPath}`)

  // Get User.age property
  const age = model.declares[0].properties[2]

  // Set age minimum to 18
  age.range.withMinimum(18)

  // Set age maximum to 120
  age.range.withMaximum(120)

  const outPath = path.join(__dirname, './generated.json')
  console.log('Generating file to:', outPath)

  // Generate AMF Graph file
  await wap.amfGraph.generateFile(model, `file://${outPath}`)
}

main()
