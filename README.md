# `webapi-parser`

[![Build status](https://img.shields.io/travis/raml-org/webapi-parser.svg?style=flat)](https://travis-ci.org/raml-org/webapi-parser)
[![Coverage Status](https://coveralls.io/repos/github/raml-org/webapi-parser/badge.svg?branch=master)](https://coveralls.io/github/raml-org/webapi-parser?branch=master)

API Spec parser based on [AMF](https://github.com/aml-org/amf). Currently supports RAML 0.8, RAML 1.0, OAS 2.0 and OAS 3.0(beta).

This project is a thin wrapper that exposes API Spec-related capabilities from [AMF](https://github.com/aml-org/amf). It is written in Scala and offered in two versions: [JavaScript](#javascript) and [Java](#java).

## 📃 Documentation
|      | JavaScript | Java |
| ---- | ---------- | ---- |
| **Installation** | [NPM](#javascript) | [Gradle/Maven](#java) |
| **Object-oriented interface** | ["WebApi" Model](https://raml-org.github.io/webapi-parser/js/classes/_webapi_parser_.webapibaseunit.html) | ["WebApi" Model](https://raml-org.github.io/webapi-parser/js/classes/_webapi_parser_.webapibaseunit.html) |
| **Package** | [![NPMJS](https://img.shields.io/npm/v/webapi-parser.svg?style=flat)](https://www.npmjs.com/package/webapi-parser) | [![Maven Central](https://img.shields.io/static/v1.svg?style=flat&logo=java&label=%20&labelColor=white&labelColor=007396&color=007396&message=Maven%20Central)](https://search.maven.org/artifact/org.raml/webapi-parser/) |
| **Examples** | [JavaScript examples](./examples/js/README.md) | [Java examples](./examples/java/README.md) |
| **Developer Documentation** | [JavaScript Typedoc](https://raml-org.github.io/webapi-parser/js/modules/_webapi_parser_.html) | [Javadocs](https://raml-org.github.io/webapi-parser/java/index.html) |

## 📦 Examples
* [Resolving a "WebApi" Model](https://raml-org.github.io/webapi-parser/resolving.html)
* [Navigating a "WebApi" Model](https://raml-org.github.io/webapi-parser/navigating.html)
* [Constructing a "WebApi" Model](https://raml-org.github.io/webapi-parser/constructing.html)
* [Translating RAML DataTypes to JSON Schemas](https://raml-org.github.io/webapi-parser/translating-raml-json.html)
* [Translating JSON Schemas to RAML DataTypes](https://raml-org.github.io/webapi-parser/translating-json-raml.html)
* [More examples](./examples)

## 🛠 Installation

### JavaScript
Install the npm package:

```sh
$ npm install webapi-parser
```

and require/reference as follows:
```js
const wap = require('webapi-parser').WebApiParser
```

Usage examples are located in the [examples directory](./examples/js/README.md).

### Java
Specify `webapi-parser` as a dependency and set both MuleSoft and Jitpack repositories.

Gradle:
```groovy
dependencies {
    compile 'org.raml:webapi-parser:x.y.z'
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
    <artifactId>webapi-parser</artifactId>
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

To install snapshot versions set additional Sonatype snapshots repository.

Gradle:
```groovy
...
repositories {
    ...
    maven {
        url 'https://oss.sonatype.org/content/repositories/snapshots'
    }
}
```

Maven:
```xml
...
<repositories>
    ...
    <repository>
        <id>SonatypeSnapshots</id>
        <url>https://oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
</repositories>
```

Usage examples are located in the [examples directory](./examples/java/README.md).

---

If you wish to contribute to this project, please review our [Contribution Guidelines](./CONTRIBUTING.md).
