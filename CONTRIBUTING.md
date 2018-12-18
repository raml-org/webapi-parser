## Contribution Guidelines

### Contributor Agreement

To contribute source code to this repository, please read our [Contributor Agreement](http://www.mulesoft.org/legal/contributor-agreement.html), and then execute it by running [this notebook](https://api-notebook.anypoint.mulesoft.com/notebooks/#380297ed0e474010ff43) and following the instructions.

## Setting-up this project locally

### Requirements
* Scala 2.12.2
* sbt 0.13.15

### Generate artifacts
To generate JavaScript artifact (generated at `./js/module/webapi-parser.js`), run:
```sh
sbt buildJS
```

To generate regular (not "fat") `.jar`, run:
```sh
sbt package
```

To generate a "fat" `.jar` (generated at `./webapi-parser-X.Y.Z.jar`), run:
```sh
sbt assembleFatJar
```
and then you can copy/move that JAR to your `libs` directory and depend on it directly:

```groovy
dependencies {
    compile files('libs/webapi-parser-X.Y.Z.jar')
}
```

## Running tests
```sh
sbt test
```
