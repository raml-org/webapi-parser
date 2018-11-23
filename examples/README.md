# `webapi-parser` examples

This project aims to provide some usage examples to learn how to use `webapi-parser`.

## Java Examples

When testing against locally built `.jar`, first build it with `sbt assembleFatJar` in `webapi-parser` root and copy it to `examples/java/libs`.

First build project and install dependencies:

```sh
$ ./gradlew build
```

In the route `examples/java/src/main/java` of the project you will find examples for each feature (parser, generator, validation, resolution) that shows the library usage from Java code. These examples are used in `co.acme.demo.WebApiParserDemo` class which can be run using Gradle `run` command:

```sh
$ ./gradlew run
```

## JavaScript Examples

In the route `examples/js` are the examples of usage from JS code.

### Example

Each file contains example of parsing, generation, validation and/or resolving of different WebApi formats.

#### Usage

1. Run `cd examples/js` from the root of the project;
2. Run `npm install webapi-parser`;
3. Run desired script with `node <script_name>.js`. E.g. `node raml10-string.js`;
