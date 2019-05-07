# webapi-parser

[![Build status](https://img.shields.io/travis/raml-org/webapi-parser.svg?style=flat)](https://travis-ci.org/raml-org/webapi-parser)

API Spec parser based on [AMF](https://github.com/aml-org/amf). Currently supports RAML 0.8, 1.0 and OAS 2.0.

This project is a thin wrapper that exposes API Spec-related capabilities from [AMF](https://github.com/aml-org/amf). It is written in Scala and offered in two versions: [JavaScript](#javascript) and [Java](#java).

## Documentation
|      | JavaScript | Java | 
| ---- | ---------- | ---- | 
| **Installation** | [NPM](#javascript) | [Gradle/Maven](#java) |
| **Documentation** | [JavaScript Typedoc](https://raml-org.github.io/webapi-parser/js/modules/_webapi_parser_.html) | [Javadocs](https://raml-org.github.io/webapi-parser/java/index.html) | 
| **Object-oriented interface** | ["WebApi" Model](https://raml-org.github.io/webapi-parser/js/classes/_webapi_parser_.webapibaseunit.html) | ["WebApi" Model](https://raml-org.github.io/webapi-parser/js/classes/_webapi_parser_.webapibaseunit.html) | 
| **Package** | [![NPMJS](https://img.shields.io/static/v1.svg?style=flat&logo=npm&label=%20&labelColor=white&color=CB3837&message=NPMJS)](https://) | [![Maven Central](https://img.shields.io/static/v1.svg?style=flat&logo=java&label=%20&labelColor=white&labelColor=007396&color=007396&message=Maven%20Central)](https://) | 


## Examples
* [Resolving a "WebApi" Model](https://raml-org.github.io/webapi-parser/resolving)
* [Navigating a "WebApi" Model](https://raml-org.github.io/webapi-parser/navigating)
* [Constructing a "WebApi" Model](https://raml-org.github.io/webapi-parser/constructing)
* [Translating RAML DataTypes to JSON Schemas](https://raml-org.github.io/webapi-parser/translating-raml-json)
* [Translating JSON Schemas to RAML DataTypes](https://raml-org.github.io/webapi-parser/translating-json-raml)

## Installation

### JavaScript
Install the npm package:

```sh
$ npm install webapi-parser
```

and require/reference as follows:
```js
const wap = require('webapi-parser').WebApiParser
```

See the [JavaScript examples directory](https://github.com/raml-org/webapi-parser/tree/master/examples/js/) for some usage examples.

### Java
Specify `webapi-parser` as a dependency and set both MuleSoft and Jitpack repositories.

Gradle:
```groovy
dependencies {
    compile 'org.raml:webapi-parser_2.12:x.y.z'
}
...
repositories {
    maven {
        url "https://repository-master.mulesoft.org/nexus/content/repositories/releases"
    }
    maven {
        url "https://jitpack.io"
    }
    mavenCentral()
}
```

Maven:
```xml
<dependency>
    <groupId>org.raml</groupId>
    <artifactId>webapi-parser_2.12</artifactId>
    <version>X.Y.Z</version>
</dependency>
...
<repositories>
    <repository>
        <id>MuleSoftReleases</id>
        <url>https://repository-master.mulesoft.org/nexus/content/repositories/releases</url>
    </repository>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

See the [Java examples directory](https://github.com/raml-org/webapi-parser/tree/master/examples/java/) for some usage examples.

---
If you wish to contribute to this project, please review our [Contribution Guidelines](https://github.com/raml-org/webapi-parser/tree/master/CONTRIBUTING.md).
