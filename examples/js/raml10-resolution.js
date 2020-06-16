/**
 * Example of resolving/expanding RAML 1.0 data types.
 */
const wap = require('webapi-parser').WebApiParser
const path = require('path')

const ramlStr = `#%RAML 1.0 Library
types:
  User:
    type: object
    properties:
      firstName: string
      lastName:  string
  Admin:
    type: User
    properties:
      permissions:
        type: array
        items: string
`

async function main () {
  // Parse RAML 1.0 string
  const model = await wap.raml10.parse(ramlStr)

  // Resolve/expand types defined in the document
  const resolved = await wap.raml10.resolve(model)

  // Generate output file with the resolved model RAML content
  const outPath = path.join(__dirname, './generated.raml')
  await wap.raml10.generateFile(resolved, `file://${outPath}`)
  // Or
  // const outputRaml = await wap.raml10.generateString(resolved)
}

main()
