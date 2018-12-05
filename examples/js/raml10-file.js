const wap = require('webapi-parser').WebApiParser
const path = require('path')

async function main () {
  const inPath = path.join(__dirname, '../api-specs/raml/api-with-types.raml')
  const model = await wap.raml10.parse(`file://${inPath}`)

  // Modify content
  const age = model.declares[0].properties[2]
  age.range.withMinimum(18)
  model.encodes.withServer('127.0.0.1/api/{version}')

  const outPath = path.join(__dirname, './generated.raml')
  console.log('Generating file to:', outPath)
  await wap.raml10.generateFile(model, `file://${outPath}`)
}

main()
