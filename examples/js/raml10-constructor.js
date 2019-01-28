const webapi = require('webapi-parser')
const wap = webapi.WebApiParser
const domain = webapi.model.domain

async function main () {
  await wap.init()

  const api = new domain.WebApi()
    .withName('Foo org API')
    .withDescription('Describes Foo org API')
    .withSchemes(['http', 'https'])
    .withContentType(['application/json'])
    .withAccepts(['application/json'])
    .withVersion('1.1')
  api.withServer('foorg.bar/{version}')

  const users = api.withEndPoint('/users')
  users.withName('Users endpoint').withDescription('Lists users')

  const getUsers = users.withOperation('get')
  getUsers.withName('GET Users')

  const getUsers500Resp = getUsers.withResponse('500')
  const getUsers500Payload = getUsers500Resp.withPayload('text/plain')
  const getUsers500Schema = getUsers500Payload.withScalarSchema(
    '500ErrorMessage')
  getUsers500Schema.withDataType('http://www.w3.org/2001/XMLSchema#string')

  const getUsers200Resp = getUsers.withResponse('200')
  const getUsers200Payload = getUsers200Resp.withPayload('application/json')
  const getUsers200Schema = getUsers200Payload.withObjectSchema('schema')
  getUsers200Schema
    .withName('Users')
    .withClosed(false)

  const userNameScalar = new domain.ScalarShape()
    .withDataType('http://www.w3.org/2001/XMLSchema#string')
  const userName = getUsers200Schema
    .withProperty('username')
    .withPath("http://a.ml/vocabularies/data#name")
    .withMinCount(1)
    .withRange(userNameScalar)

  const userEmailScalar = new domain.ScalarShape()
    .withDataType('http://www.w3.org/2001/XMLSchema#string')
  const userEmail = getUsers200Schema
    .withProperty('email')
    .withMinCount(1)
    .withPath('http://a.ml/vocabularies/data#email')
    .withRange(userEmailScalar)

  const user = api.withEndPoint('/user/{id}')
  user.withName('User endpoint').withDescription('Get user')

  const model = new webapi.model.document.Document(api)
  const generated = await wap.raml10.generateString(model)
  console.log('Generated from constructed:\n', generated)
}

main()
