const webapi = require('webapi-parser')
const wap = webapi.WebApiParser
const domain = webapi.model.domain

async function main () {
  const server = new domain.Server()
    .withUrl('foorg.bar/{version}')

  // GET /users 200
  const userEmailScalar = new domain.ScalarShape()
    .withDataType('http://www.w3.org/2001/XMLSchema#string')

  const userEmail = new domain.PropertyShape()
    .withMinCount(1)
    .withPath('http://a.ml/vocabularies/data#email')
    .withNode(userEmailScalar)

  const userNameScalar = new domain.ScalarShape()
    .withDataType('http://www.w3.org/2001/XMLSchema#string')

  const userName = new domain.PropertyShape()
    .withMinCount(1)
    .withPath('http://a.ml/vocabularies/data#name')
    .withNode(userNameScalar)

  const getUsers200Schema = new domain.NodeShape()
    .withClosed(false)
    /*
      TODO:
      Adding properties is broken for some reason. May be possible to
      rework with domain.Payload.withObjectSchema() but that's broken
      in AMF as well.
    */
    // .withProperties([userName, userEmail])

  const getUsers200Payload = new domain.Payload()
    .withMediaType('application/json')
    .withSchema(getUsers200Schema)

  const getUsers200Resp = new domain.Response()
    .withStatusCode('200')
    .withPayloads([getUsers200Payload])

  // GET /users 500
  const getUsers500Schema = new domain.ScalarShape()
    .withDisplayName('500ErrorMessage')
    .withDataType('http://www.w3.org/2001/XMLSchema#string')

  const getUsers500Payload = new domain.Payload()
    .withMediaType('text/plain')
    .withSchema(getUsers500Schema)

  const getUsers500Resp = new domain.Response()
    .withStatusCode('500')
    .withPayloads([getUsers500Payload])

  // GET /users
  const getUsers = new domain.Operation()
    .withMethod('get')
    .withName('GET Users')
    .withResponses([getUsers500Resp, getUsers200Resp])

  // /users
  const users = new domain.EndPoint()
    .withPath('/users')
    .withName('Users endpoint')
    .withDescription('Lists users')
    .withOperations([getUsers])

  // /users/{id}
  const user = new domain.EndPoint()
    .withPath('/users/{id}')
    .withName('User endpoint')
    .withDescription('Get user')

  const api = new domain.WebApi()
    .withName('Foo org API')
    .withDescription('Describes Foo org API')
    .withSchemes(['http', 'https'])
    .withContentType(['application/json'])
    .withAccepts(['application/json'])
    .withVersion('1.1')
    .withServers([server])
    .withEndPoints([users, user])

  const model = new webapi.model.document.Document().withEncodes(api)
  const generated = await wap.raml10.generateString(model)
  console.log('Generated from constructed:\n', generated)
}

main()
