# webapi-parser
API Spec parser based on AMF. Supports OAS 2.0, and RAML 0.8, 1.0.


## Installation

### JS
Install npm package with

```sh
$ npm install webapi-parser
```

Start using it with

```js
const wap = require('webapi-parser').WebApiParser
```

Check [JS examples directory](examples/js/) for more usage examples.

### Java
To use, specify `webapi-parser` dependency.

Gradle example:

```groovy
dependencies {
    compile 'org.raml:webapi-parser_2.12:x.y.z'
}
```

Maven example:

```xml
<dependency>
    <groupId>org.raml</groupId>
    <artifactId>webapi-parser_2.12</artifactId>
    <version>x.y.z</version>
</dependency>
```

NOTE: you may use the `-SNAPSHOT` versions of the JVM artifacts at your own risk since those snapshot versions may contain breaking changes.

Check [Java examples directory](examples/java/) for more usage examples.


## Generate artifacts directly from cloned repository

### Requirements
* Scala 2.12.2
* sbt 0.13.15

### Generate JS artifact
To generate JS artifact to `./js/module/webapi-parser.js` run

```sh
sbt buildJS
```

### Generate Java artifact
To generate regular (not "fat") `.jar` run

```sh
sbt package
```

To generate "fat" `.jar` to `./webapi-parser-X.Y.Z.jar` run

```sh
sbt assembleFatJar
```
