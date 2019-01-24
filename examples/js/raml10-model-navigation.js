const path = require('path')
const wap = require('webapi-parser').WebApiParser

async function main () {
  const inPath = path.join(
    __dirname, '../api-specs/raml/navigation-example-api.raml')
  const model = await wap.raml10.parse(`file://${inPath}`)

  const api = model.encodes

  // API root properties
  console.log('Title:', api.name.value())
  console.log('Description:', api.description.value())
  console.log('First doc title:', api.documentations[0].title.value())
  console.log('First doc content:', api.documentations[0].description.value())
  console.log('Version:', api.version.value())
  console.log('First protocol:', api.schemes[0].value())
  console.log('Base uri:', api.servers[0].url.value())

  // Access security scheme from root
  console.log('First security scheme name:', api.security[0].name.value())
  console.log(
    'First security scheme description:',
    api.security[0].scheme.description.value())

  // Endpoint /users
  const users = api.endPoints[0]
  console.log('Path:', users.path.value())
  console.log('Relative path:', users.relativePath)
  console.log('Resource type:', users.extendsNode[0].name.value())

  // Access resource type properties from endpoint
  const refRt = users.extendsNode[0].target.linkTarget.dataNode
  console.log('Methods:', Object.keys(refRt.properties))
  console.log(
    'Page query param "csrf" type:',
    refRt
      .properties.post
      .properties.queryParameters
      .properties.csrf
      .properties.type.value)

  // POST /users
  const postUsers = users.operations[0]

  // Access trait properties from endpoint
  console.log('Trait:', postUsers.extendsNode[0].name.value())
  const refTr = postUsers.extendsNode[0].target.linkTarget.dataNode
  console.log(
    'Trait description:',
    refTr
      .properties.headers
      .properties['X-Tracker']
      .properties.description.value)

  const postUsersReq = postUsers.request.payloads[0]
  console.log('Request media type:', postUsersReq.mediaType.value())

  // Access User type from request
  const postUsersType = postUsersReq.schema.inherits[0].linkTarget
  console.log('Type name:', postUsersType.name.value())
  console.log(
    'Second property name:',
    postUsersType.properties[1].name.value())
  console.log(
    'Second property name:',
    postUsersType.properties[1].range.dataType.value())

  // Endpoint /users/{id}
  const user = api.endPoints[1]
  console.log('Path:', user.path.value())
  console.log('Relative path:', user.relativePath)
  console.log(
    'First annotation name:',
    user.customDomainProperties[0].name.value())
  console.log(
    'First annotation value:',
    user.customDomainProperties[0].extension.value)

  // GET /users/{id}
  const getUser = user.operations[0]
  console.log('Status code:', getUser.responses[0].statusCode.value())
  const getUserResp = getUser.responses[0].payloads[0]
  console.log('Response media type:', getUserResp.mediaType.value())

  // Access User type from response
  const getUserType = getUserResp.schema.inherits[0].linkTarget
  console.log('Type name:', getUserType.name.value())
  console.log(
    'First property name:',
    getUserType.properties[0].name.value())
  console.log(
    'First property name:',
    getUserType.properties[0].range.dataType.value())

  // Annotation 'experimental'
  const annotation = model.declares[0]
  console.log('Annotation name:', annotation.name.value())
  console.log('Annotation type:', annotation.schema.dataType.value())

  // Type 'User'
  const userType = model.declares[1]
  console.log('User type properties:')
  userType.properties.forEach((prop) => {
    console.log(prop.name.value(), prop.range.dataType.value())
  })
  const age = userType.properties[2]
  console.log(
    'Age from', age.range.minimum.value(),
    'to', age.range.maximum.value())

  // ResourceType 'postable'
  const postable = model.declares[2]
  console.log(
    'Postable csrf query param type:',
    postable.dataNode
      .properties.post
      .properties.queryParameters
      .properties.csrf
      .properties.type.value)

  // Trait 'traceable'
  const traceable = model.declares[3]
  const header = traceable.dataNode
    .properties.headers
    .properties['X-Tracker']
  console.log(
    'Traceable header description:',
    header.properties.description.value)
  console.log(
    'Traceable header pattern:',
    header.properties.pattern.value)
  console.log(
    'Traceable header example:',
    header.properties.example.value)

  // SecurityScheme oauth_1_0
  const oauth1 = model.declares[4]
  console.log('Description:', oauth1.description.value())
  console.log('Type:', oauth1.type.value())
  console.log(
    'requestTokenUri:',
    oauth1.settings.requestTokenUri.value())
  console.log(
    'authorizationUri:',
    oauth1.settings.authorizationUri.value())
  console.log(
    'tokenCredentialsUri:',
    oauth1.settings.tokenCredentialsUri.value())
}

main()
