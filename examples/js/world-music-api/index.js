// Installed with `npm link /path/to/webapi-parser/js/module/`
// after running `sbt buildJS` in webapi-parser project.
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
          maximum: 125
  /users/{id}:
    get:
      responses:
        200:
          body:
            application/json:
              type: User
`

function testWap () {
  let model
  wap.init().then(function () {
    return wap.raml10.parseString(ramlStr)
    // const fpath = path.join(__dirname, './spec/api.raml')
    // return wap.raml10.parseFile(`file://${fpath}`)
  })
  .then(function (m) {
    model = m
    console.log('> Parsed RAML1.0', model)
    return wap.oas20.generateString(model)
  })
  .then(function (generated) {
    console.log('> Generated OAS2', generated)
    return wap.raml10.validate(model)
  })
  .then(function (report) {
    console.log('> RAML validation report', report.results)
  })
  .catch(function (err) {
    console.log(err)
  })
}

testWap()
