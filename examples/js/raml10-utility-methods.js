/**
 * Example of using utility methods to get data from RAML 1.0 doc.
 */
const wap = require('webapi-parser').WebApiParser
const path = require('path')

const ramlStr = `
  #%RAML 1.0 DataType
  type: object
  properties:
    firstName: string
    lastName:  string
    age:
      type: integer
      minimum: 0
      maximum: 99
`

async function main () {
  // Navigating RAML 1.0 file
  const fpath = path.join(__dirname, '../api-specs/raml/api-with-types.raml')
  const model = await wap.raml10.parse(`file://${fpath}`)

  // Get all types (both defined in root and in endpoints)
  const allTypes = model.findByType('http://www.w3.org/ns/shacl#NodeShape')

  // Get type defined in root by name
  const userInRoot = model.getDeclarationByName('User')
  console.log(userInRoot.toJsonSchema())

  // Edit type properties
  const age = userInRoot.properties[2]
  age.range.withMaximum(120)

  // Navigating RAML 1.0 DataType string
  const stringModel = await wap.raml10.parse(ramlStr)

  // Get type defined in root by name. Types without explicit name have a
  // special name "type"
  const userInStringRoot = stringModel.getDeclarationByName('type')
  console.log(userInStringRoot.toJsonSchema())
}

main()
