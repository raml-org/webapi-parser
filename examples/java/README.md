## About
This code aims to provide some usage examples to learn how to use `webapi-parser` in Java projects.

## Installation
Please follow `webapi-parser` [Java installation instructions](../../README.md#java).

When testing against locally built ("fat") `.jar`, copy it to [libs folder](libs) and specify it as dependency in `build.gradle` like so

```gradle
dependencies {
    compile files('libs/webapi-parser-0.0.1.jar')
}
```

Build project and install dependencies:

```sh
$ ./gradlew build
```

## How to run
In the [src/main/java](src/main/java) folder you will find examples for each feature (parser, generator, validation, resolution) that shows the library usage from Java code. These examples are used in `co.acme.demo.WebApiParserDemo` class which can be run using Gradle `run` command:

```sh
$ ./gradlew run
```
