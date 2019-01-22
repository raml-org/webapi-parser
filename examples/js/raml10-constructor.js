const webapi = require('webapi-parser')
const wap = webapi.WebApiParser
const domain = webapi.model.domain

// async function main () {
function main () {
  const server = new domain.Server()
    .withUrl('Foorg.bar/{version}')

  // GET /users 500
  const getUsers500Schema = new domain.ScalarShape()
    .withDisplayName('500 error message')
    .withDataType('http://www.w3.org/2001/XMLSchema#string')

  const getUsers500Payload = new domain.Payload()
    .withMediaType('text/plain')
    .withSchema(getUsers500Schema)

  const getUsers500Resp = new domain.Response()
    .withStatusCode('500')
    .withPayloads([getUsers500Payload])

  // GET /users 200
  const userNameScalar = new domain.ScalarShape()
    .withDataType('http://www.w3.org/2001/XMLSchema#string')

  const userName = new domain.PropertyShape()
    .withPath("http://a.ml/vocabularies/data#string")
    .withMinCount(1)
    .withNode(userNameScalar)

  const getUsers200Schema = new domain.NodeShape()
    .withClosed(false)
    .withProperties([userName])

  const getUsers200Payload = new domain.Payload()
    .withMediaType('application/json')
    .withSchema(getUsers200Schema)

  const getUsers200Resp = new domain.Response()
    .withStatusCode('200')
    .withPayloads([getUsers200Payload])

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

  const api = new domain.WebApi()
    .withName('Foo org API')
    .withDescription('Describes Foo org API')
    .withSchemes(['http', 'https'])
    .withContentType(['application/json'])
    .withAccepts(['application/json'])
    .withVersion('1.1')
    .withServers([server])
    .withEndPoints([users])

  // const request = operation
  //   .withRequest()

  // //shape of param1
  // const param1Shape = request
  //   .withQueryParameter('param1')
  //   .withParameterName('param1')
  //   .withDescription('Some descr')
  //   .withBinding('query')
  //   .withRequired(true)
  //   .withObjectSchema('schema')

  // param1Shape
  //   .withDescription('Some descr')
  //   .withClosed(false)
  //   .withProperty('name')
  //   .withMinCount(1)
  //   .withPath('http://a.ml/vocabularies/data#name')
  //   .withScalarSchema('name')
  //   .withDataType('http://www.w3.org/2001/XMLSchema#string')
  // param1Shape
  //   .withProperty('lastName')
  //   .withMinCount(1)
  //   .withPath('http://a.ml/vocabularies/data#lastName')
  //   .withScalarSchema('lastName')
  //   .withDataType('http://www.w3.org/2001/XMLSchema#string')
  // const address = param1Shape
  //   .withProperty('address')
  //   .withMinCount(1)
  //   .withPath('http://a.ml/vocabularies/data#address')
  //   .withObjectRange('address')
  // address
  //   .withClosed(false)
  //   .withProperty('city')
  //   .withPath('http://a.ml/vocabularies/data#city')
  //   .withMinCount(1)
  //   .withScalarSchema('city')
  //   .withDataType('http://www.w3.org/2001/XMLSchema#string')
  // address
  //   .withProperty('postal')
  //   .withMinCount(1)
  //   .withPath('http://a.ml/vocabularies/data#postal')
  //   .withScalarSchema('postal')
  //   .withDataType('http://www.w3.org/2001/XMLSchema#integer')
  // //shape of param1

  // //shape of param2
  // request
  //   .withQueryParameter('param2')
  //   .withParameterName('param2')
  //   .withBinding('query')
  //   .withRequired(false)
  //   .withScalarSchema('schema')
  //   .withDataType('http://www.w3.org/2001/XMLSchema#string')

  // //param3 typeless , default type its string?
  // request
  //   .withQueryParameter('param3')
  //   .withParameterName('param3')
  //   .withRequired(true)
  //   .withBinding('query')
  //   .withDescription('typeless')
  //   .withScalarSchema('schema')
  //   .withDataType('http://www.w3.org/2001/XMLSchema#string')
  //   .withDescription('typeless')

  // //headers
  // //header type string
  // request
  //   .withHeader('Header-One')
  //   .withParameterName('Header-One')
  //   .withBinding('header')
  //   .withRequired(false)
  //   .withScalarSchema('schema')
  //   .withDataType('http://www.w3.org/2001/XMLSchema#string')
  //   .withXMLSerialization(
  //     XMLSerializer()
  //       .withAttribute(true)
  //       .withWrapped(false)
  //       .withName('Xml-name')
  //       .withNamespace('xsd')
  //       .withPrefix('pref'))

  // //header with object type
  // const header2Type =
  //   request
  //     .withHeader('header-two')
  //     .withParameterName('header-two')
  //     .withRequired(true)
  //     .withBinding('header')
  //     .withObjectSchema('schema')
  // header2Type
  //   .withClosed(false)
  //   .withProperty('number')
  //   .withPath('http://a.ml/vocabularies/data#number')
  //   .withMinCount(1)
  //   .withScalarSchema('number')
  //   .withDataType('http://www.w3.org/2001/XMLSchema#integer')

  // //body operation payload
  // request
  //   .withPayload('application/raml')
  //   .withScalarSchema('schema')
  //   .withDataType('http://www.w3.org/2001/XMLSchema#string')

  // //payload of body operation with object
  // request
  //   .withPayload('application/json')
  //   .withObjectSchema('schema')
  //   .withClosed(false)
  //   .withProperty('howmuch')
  //   .withPath('http://a.ml/vocabularies/data#howmuch')
  //   .withMinCount(1)
  //   .withScalarSchema('howmuch')
  //   .withDataType('http://www.w3.org/2001/XMLSchema#integer')

  // //responses

  // const default = operation
  //   .withResponse('200')

  // default
  //   .withPayload('application/json')
  //   .withObjectSchema('schema')
  //   .withClosed(false)
  //   .withProperty('invented')
  //   .withPath('http://a.ml/vocabularies/data#invented')
  //   .withMinCount(1)
  //   .withScalarSchema('invented')
  //   .withDataType('http://www.w3.org/2001/XMLSchema#string')
  // default
  //   .withPayload('application/xml')
  //   .withScalarSchema('schema')
  //   .withDataType('http://www.w3.org/2001/XMLSchema#string')

  // // array operations

  // api
  //   .withEndPoint('/scalar_array')
  //   .withOperation('get')
  //   .withName('scalar_array')
  //   .withRequest()
  //   .withPayload('application/json')
  //   .withArraySchema('schema')
  //   .withDisplayName('scalar_array')
  //   .withMinItems(3)
  //   .withMaxItems(10)
  //   .withUniqueItems(true)
  //   .withScalarItems()
  //   .withName('items')
  //   .withDataType('http://www.w3.org/2001/XMLSchema#string')

  // api
  //   .withEndPoint('/object_array')
  //   .withOperation('get')
  //   .withName('object_array')
  //   .withRequest()
  //   .withPayload('application/json')
  //   .withArraySchema('schema')
  //   .withDisplayName('object_array')
  //   .withNodeItems()
  //   .withClosed(false)
  //   .withName('items')

  let model = new webapi.model.document.Document().withEncodes(api)

  return wap.raml10.generateString(model)
    .then((generated) => {
      console.log('Generated from constructed:\n', generated)
    })
    .catch((e) => {
      console.log('>>>', e)
    })

  // const generated = await wap.raml10.generateString(model)
  // console.log('Generated from constructed:\n', generated)
}

// main()
return main()
