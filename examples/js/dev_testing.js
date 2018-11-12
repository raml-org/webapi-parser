// Installed with `npm link /path/to/webapi-parser/js/module/`
// after running `sbt buildJS` in webapi-parser project.
const wap = require('webapi-parser').WebApiParser
const path = require('path')

const raml10Parser = wap.raml10Parser()
const oas20Generator = wap.oas20Generator()

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
  let baseUnit
  wap.init().then(function () {
    return raml10Parser.parseStringAsync(ramlStr)
    // const fpath = path.join(__dirname, '../apis/world-music-api/api.raml')
    // return raml10Parser.parseFileAsync(`file://${fpath}`)
  })
  .then(function (bu) {
    baseUnit = bu
    console.log('>> Parsed RAML1.0', baseUnit)
    return oas20Generator.generateString(baseUnit)
  })
  .then(function (generated) {
    console.log('>> Generated OAS2', generated)
    return wap.validateRaml10(baseUnit)
  })
  .then(function (report) {
    console.log('>> RAML validation report', report.results)
  })
  .catch(function (err) {
    console.log(err)
  })
}

testWap()
