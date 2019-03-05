/**
 * Example of using utility methods to get data from RAML 1.0 doc.
 */
const wap = require('webapi-parser').WebApiParser
const path = require('path')

const ramlStr = `
  #%RAML 1.0
  title: API with Types
  types:
    User:
      type: object
      properties:
        firstName: string
        lastName:  string
        age:
          type: integer
          minimum: 0
          maximum: 99
  /users/{id}:
    get:
      responses:
        200:
          body:
            application/json:
              type: User
`

async function main () {
  // Navigating RAML 1.0 file
  const fpath = path.join(__dirname, '../api-specs/raml/api-with-types.raml')
  let docPath = `file://${fpath}`
  const model = await wap.raml10.parse(docPath)

  // Get all types (both defined in root and in endpoints)
  const allTypes = model.findByType("http://www.w3.org/ns/shacl#NodeShape")

  // Get type defined in root
  // An ID to get type from root will look like:
  // file:///somewhere/api-specs/raml/api-with-types.raml#/declarations/types/User
  const userInRoot = model.findById(`${docPath}#/declarations/types/User`)
  // Edit type properties
  const age = userInRoot.properties[2]
  age.range.withMaximum(120)

  // Get type reference in /users/{id} response schema.
  // Note that values from RAML must be url-encoded to be used by
  // utility functions
  const endpoint = encodeURIComponent('/users/{id}')
  const ct = encodeURIComponent('application/json')
  const userRefFromEndpoint = model.findById(
    `${docPath}#/web-api/end-points/${endpoint}/get/200/${ct}/schema`)

  // Get type which is referenced by `userRefFromEndpoint`
  const userInEndpoint = userRefFromEndpoint.inherits[0].linkTarget
  console.log(userInEndpoint.id === userInRoot.id)


  // Navigating RAML 1.0 string
  const stringModel = await wap.raml10.parse(ramlStr)

  // The only difference from "working with files" is that ID strings
  // should include default AML document path instead of a file system
  // path. An ID to get type from root would look like:
  // http://a.ml/amf/default_document#/declarations/types/User
  docPath = 'http://a.ml/amf/default_document'
  const userInStringRoot = stringModel.findById(`${docPath}#/declarations/types/User`)
}

main()
