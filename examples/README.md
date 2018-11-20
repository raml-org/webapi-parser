# WebApiParser Examples

This project aims to provide some usage examples to learn how to use AMF.

## Java Examples

```sh
$ ./gradlew build
```

In the route src/main/java/example of the project you will find examples for each module (parser, generator, creator, validation, etc) that shows the library usage from Java code. These examples can be run from the main class _TestMainClass_ or using Gradle `run`.

```sh
$ ./gradlew run
```

## JavaScript Examples

In the route src/main/js of the project there are the examples of usage from JS code.

### Example

Each module (parse, generator, creator...) contains examples of use of the different functions of AMF.
This example has a node.js file index.js where the amf library will be imported and can be used as a node module.

#### Usage

1. Run *cd src/main/js/example* from the root of the project.
2. Run *npm install*.
3. Run *npm start*.
4. Open *localhost:3000* in the browser.
You can see index.js with examples of use and play with the module. Modify parser and generator modules to change the server-side use of the library.

### Converter

In this route you also have another js project, the converter.
This is a node project that demonstrates how amf parses and generates an OAS/RAML document. Note that it's not a conversion tool per se as you can quickly see in the code. AMF will build the model every time, dumping the required spec output when selected.

#### Usage

1. Run *cd src/main/js/converter* from the root of the project.
2. Create directory *"public/build"*.
3. Run *npm install*.
4. Run *npm start*.
5. Open *localhost:3000* in the browser.

You can now start trying AMF by reading and dumping from/to different API Design specs.
