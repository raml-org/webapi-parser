const wap = require('webapi-parser').WebApiParser

async function main () {
  const inPath = path.join(
    __dirname, '../api-specs/raml/navigation-example-api.raml')
  const model = await wap.raml10.parse(`file://${inPath}`)


}

main()
