# webapi-parser
API Spec parser based on [AMF](https://github.com/aml-org/amf). Currently supports RAML 0.8, 1.0 and OAS 2.0.

This project is a thin wrapper that exposes API Spec-related capabilities from [AMF](https://github.com/aml-org/amf). It is written in Scala and offered in two versions: [JavaScript](#javascript) and [Java](#java).

## Documentation
* [RAML Data Type -> JSON Schema conversion](https://raml-org.github.io/webapi-parser/common/conversion-raml-json)

## JavaScript

### Documentation
* [JavaScript API](https://raml-org.github.io/webapi-parser/js/modules/_webapi_parser_.html)
* [AMF Model](https://raml-org.github.io/webapi-parser/js/classes/_amf_client_js_.model.document.baseunit.html)

### Installation
Install the npm package:

```sh
$ npm install webapi-parser
```

and then require/reference as follows:
```js
const wap = require('webapi-parser').WebApiParser
```

You can check the [JavaScript examples directory](examples/js/) for some usage examples.

## Java

### Documentation
* [Java API](https://raml-org.github.io/webapi-parser/java/index.html)
* [AMF Model](https://raml-org.github.io/webapi-parser/js/classes/_amf_client_js_.model.document.baseunit.html)

### Installation
To use, you'll need to specify `webapi-parser` as a dependency and set both MuleSoft and Jitpack repositories.

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

You can check the [Java examples directory](examples/java/) for some usage examples.

---
If you wish to contribute to this project, see our [Contribution Guidelines](./CONTRIBUTING.md).
