/**
 * Example of parsing and generating RAML 1.0 files.
 */
const wap = require('webapi-parser').WebApiParser
const path = require('path')

async function main () {
  // Parse RAML 1.0 file
  const inPath = path.join(__dirname, '../api-specs/raml/api-with-types.raml')
  const model = await wap.raml10.parse(`file://${inPath}`)

  // Get User.age property
  const age = model.declares[0].properties[2]

  // Set age minimum to 18
  age.range.withMinimum(18)

  // Set API baseUrl
  model.encodes.withServer('127.0.0.1/api/{version}')

  const outPath = path.join(__dirname, './generated.raml')
  console.log('Generating file to:', outPath)

  // Generate RAML 1.0 file
  await wap.raml10.generateFile(model, `file://${outPath}`)
}

main()
