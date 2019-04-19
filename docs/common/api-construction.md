# API construction
Using `webapi-parser` it is possible to construct an API in all supported formats by hand. These construction methods can also be used to edit a parsed document.

Below is a simplified example of constructing RAML 1.0 API.

Please refer to [complete examples](#complete-examples) for more advanced use cases and [WebApi Model API](https://raml-org.github.io/webapi-parser/js/classes/_webapi_parser_.webapibaseunit.html) for API construction methods.

## Quick start
To get started we need to import all necessary modules and initialize `WebApiParser` like so:

```js
// js
const webapi = require('webapi-parser')
const wap = webapi.WebApiParser
const domain = webapi.model.domain

async function main () {
  await wap.init()

}

main()
```

```java
// java
package co.acme.model;

import webapi.WebApiParser;
import amf.client.model.document.Document;
import amf.client.model.domain.*;

import java.util.concurrent.ExecutionException;
import java.util.Arrays;

public class Raml10Building {
  public static void buildApi() throws InterruptedException, ExecutionException {
    WebApiParser.init().get();

  }
}
```

Then to start constructing an API we have to create an instance of `WebApi` which will eventually be added to an instance of `Document`:

```js
// js
async function main () {
  ...
  const api = new domain.WebApi()
  ...
  const model = new webapi.model.document.Document(api)
}
```

```java
// java
public class Raml10Building {
  public static void buildApi() throws InterruptedException, ExecutionException {
    ...
    final WebApi api = new WebApi();
    ...
    Document model = new Document(api);
  }
}
```

Now we can start adding more elements to our API like endpoints, methods, responses, protocols, etc.

First lets specify API title and protocols:
```js
// js
async function main () {
  ...
  const api = new domain.WebApi()
    .withName('Foo org API')
    .withSchemes(['http', 'https'])
  ...
}
```

```java
// java
public class Raml10Building {
  public static void buildApi() throws InterruptedException, ExecutionException {
    ...
    final WebApi api = new WebApi()
        .withName("Foo org API")
        .withSchemes(Arrays.asList("http", "https"));
    ...
  }
}
```

Next let's add an few endpoints with methods:
```js
// js
async function main () {
  ...
  const api = new domain.WebApi()
    .withName('Foo org API')
    .withSchemes(['http', 'https'])
  const users = api.withEndPoint('/users')
  const postUsers = users.withOperation('post')
  const user = api.withEndPoint('/users/{id}')
  const getUser = user.withOperation('get')
  ...
}
```

```java
// java
public class Raml10Building {
  public static void buildApi() throws InterruptedException, ExecutionException {
    ...
    final WebApi api = new WebApi()
        .withName("Foo org API")
        .withSchemes(Arrays.asList("http", "https"));
    final EndPoint users = api.withEndPoint("/users");
    final Operation postUsers = users.withOperation("post");
    final EndPoint user = api.withEndPoint("/users/{id}");
    final Operation getUser = user.withOperation("get");
    ...
  }
}
```

Starting from here we can add more endpoints, request/response bodies, headers, etc. For that refer to complete examples.

In the end, the API document we constructed can be used with `webapi-parser` functions that accept [WebApi Model instance](https://raml-org.github.io/webapi-parser/js/classes/_webapi_parser_.webapibaseunit.html). E.g. to generate RAML 1.0 string we could do:

```js
// js
async function main () {
  ...
  const generated = await wap.raml10.generateString(model)
}

```

```java
// java
...
import webapi.Raml10;

public class Raml10Building {
  public static void buildApi() throws InterruptedException, ExecutionException {
    ...
    final String generated = Raml10.generateString(model).get();
  }
}
```

## Complete examples
* [JavaScript example](https://github.com/raml-org/webapi-parser/blob/master/examples/js/raml10-constructor.js)
* [Java example](https://github.com/raml-org/webapi-parser/blob/master/examples/java/src/main/java/co/acme/model/Raml10Building.java)
