const wap = require('webapi-parser').WebApiParser
const path = require('path')

async function main () {
  const inPath = path.join(__dirname, '../api-specs/oas/api-with-types.json')
  const model = await wap.oas20.parse(`file://${inPath}`)

  // Modify content
  const age = model.declares[0].properties[2]
  age.range.withMinimum(18)
  age.range.withMaximum(120)

  const outPath = path.join(__dirname, './generated.json')
  console.log('Generating file to:', outPath)
  await wap.oas20.generateFile(model, `file://${outPath}`)
}

main()
