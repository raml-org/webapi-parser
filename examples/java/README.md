## About

This code aims to provide some usage examples to learn how to use `webapi-parser` in Java projects.

## How to run

When testing against locally built `.jar`, first build it with `sbt assembleFatJar` in `webapi-parser` root and copy it to `examples/java/libs`.

First build project and install dependencies:

```sh
$ ./gradlew build
```

In the route `examples/java/src/main/java` of the project you will find examples for each feature (parser, generator, validation, resolution) that shows the library usage from Java code. These examples are used in `co.acme.demo.WebApiParserDemo` class which can be run using Gradle `run` command:

```sh
$ ./gradlew run
```
