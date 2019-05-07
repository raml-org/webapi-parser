# Navigating a "WebApi" Model
Using `webapi-parser` it is possible to navigate parsed document and extract data like response codes, titles, descriptions, data types, etc., from it.

Below is a simplified example of navigating RAML 1.0 API.

Please refer to [complete examples](#complete-examples) for more advanced use cases and [WebApi Model API](https://raml-org.github.io/webapi-parser/js/classes/_webapi_parser_.webapibaseunit.html) for WebApi Model navigation and data extraction methods.

## Quick start
To get started let's import all necessary modules and parse [a RAML 1.0 API document](https://github.com/raml-org/webapi-parser/blob/master/examples/api-specs/raml/navigation-example-api.raml):

```js
// js
const path = require('path')
const wap = require('webapi-parser').WebApiParser

async function main () {
  const inPath = path.join(
    __dirname, '../api-specs/raml/navigation-example-api.raml')
  const model = await wap.raml10.parse(`file://${inPath}`)

}

main()
```

```java
// java
package co.acme.model;

import webapi.Raml10;
import amf.client.model.document.Document;
import amf.client.model.domain.*;

import java.util.concurrent.ExecutionException;

public class Raml10Navigation {
  public static void navigateApi() throws InterruptedException, ExecutionException {
    String inp = "file://../api-specs/raml/navigation-example-api.raml";
    Document model = (Document) Raml10.parse(inp).get();

  }
}
```

Now we can access encoded API root properties, endpoints, methods, responses, etc.:

```js
// js
async function main () {
  ...
  const api = model.encodes
  console.log('Title:', api.name.value())
  console.log('First protocol:', api.schemes[0].value())

  // Endpoint /users
  const users = api.endPoints[0]
  console.log('Path:', users.path.value())

  // POST /users
  const postUsers = users.operations[0]
  console.log(
    'Request media type:',
    postUsers.request.payloads[0].mediaType.value())

  // Endpoint /users/{id}
  const user = api.endPoints[1]

  // GET /users/{id}
  const getUser = user.operations[0]
  console.log('Status code:', getUser.responses[0].statusCode.value())
}
```

```java
// java
public class Raml10Navigation {
  public static void navigateApi() throws InterruptedException, ExecutionException {
    ...
    WebApi api = (WebApi) model.encodes();
    System.out.println("Title: " + api.name().value());
    System.out.println("First protocol: " + api.schemes().get(0).value());

    // Endpoint /users
    EndPoint users = (EndPoint) api.endPoints().get(0);
    System.out.println("Path: " + users.path().value());

    // POST /users
    Operation postUsers = (Operation) users.operations().get(0);
    Payload postUsersReq = (Payload) ((Request) postUsers.request()).payloads().get(0);
    System.out.println("Request media type: " + postUsersReq.mediaType().value());

    // Endpoint /users/{id}
    EndPoint user = (EndPoint) api.endPoints().get(1);

    // GET /users/{id}
    Operation getUser = (Operation) user.operations().get(0);
    System.out.println("Status code: " + getUser.responses().get(0).statusCode().value());
  }
}
```

It's also possible to access data defined in document root like Data Types, annotations, security schemes, etc. and their properties:

```js
// js
async function main () {
  ...
  // Annotation 'experimental'
  const annotation = model.declares[0]
  console.log('Annotation type:', annotation.schema.dataType.value())

  // Type 'User'
  const userType = model.declares[1]
  const age = userType.properties[2]
  console.log(
    'Age from', age.range.minimum.value(),
    'to', age.range.maximum.value())

  // SecurityScheme oauth_1_0
  const oauth1 = model.declares[4]
  console.log(
    'requestTokenUri:',
    oauth1.settings.requestTokenUri.value())
}
```

```java
// java
public class Raml10Navigation {
  public static void navigateApi() throws InterruptedException, ExecutionException {
    ...
    // Annotation 'experimental'
    CustomDomainProperty annotation = (CustomDomainProperty) model.declares().get(0);
    System.out.println("Annotation type: " + ((ScalarShape) annotation.schema()).dataType());

    // Type 'User'
    NodeShape userType = (NodeShape) model.declares().get(1);
    ScalarShape age = (ScalarShape) userType.properties().get(2).range();
    System.out.println("Age from " + age.minimum().value() + " to " + age.maximum().value());

    // SecurityScheme oauth_1_0
    SecurityScheme oauth1 = (SecurityScheme) model.declares().get(4);
    System.out.println("requestTokenUri: " + settings.requestTokenUri().value());
  }
}
```

Note that it's also possible to use WebApi Model utility methods to get data from it. Please refer to [complete examples](#complete-examples) for more details.

## Complete examples
* [JavaScript example](https://github.com/raml-org/webapi-parser/blob/master/examples/js/raml10-model-navigation.js)
* [Java example](https://github.com/raml-org/webapi-parser/blob/master/examples/java/src/main/java/co/acme/model/Raml10Navigation.java)
* [Utility methods example (JS)](https://github.com/raml-org/webapi-parser/blob/master/examples/js/raml10-utility-methods.js)
* [Utility methods example (Java)](https://github.com/raml-org/webapi-parser/blob/master/examples/java/src/main/java/co/acme/model/Raml10UtilityMethods.java)
