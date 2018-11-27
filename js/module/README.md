## Notes

1. For this to work, [this PR](https://github.com/aml-org/amf/pull/411) needs to merged first because `amf-client-js` typings are incorrect.

2. Because if [this issue](https://github.com/TypeStrong/typedoc/issues/319) it's currently only possible to include or exclude ALL lib dependencies from typedoc generation. When generating docs with dependencies, it's possible to navigate to `amf-client-js` model API docs from `webapi-parser` API docs.

## Generating TypeDoc docs

1. Install dependencies:

```sh
$ npm install amf-client-js
$ npm install typedoc
```

2. Because it's not possible to install `amf-client-js` typings with `npm` yet, copy them by hand:

```sh
$ mkdir -p ./node_modules/@types/amf-client-js/
$ cp ./node_modules/amf-client-js/typings/amf-client-js.d.ts ./node_modules/@types/amf-client-js/amf-client-js.d.ts
```

When `amf-client-js` typings are hosted on npm, it should be possible to replace this step with:

```sh
$ npm install $types/amf-client-js
```

3. Generate docs:

```sh
$ ./node_modules/typedoc/bin/typedoc --out ./docs/ ./typings/webapi-parser.d.ts --includeDeclarations --excludeExternals --externalPattern "**/node_modules/**" --mode file
```

Or with external libs:

```sh
$ ./node_modules/typedoc/bin/typedoc --out ./docs/ ./typings/webapi-parser.d.ts --includeDeclarations --mode file
```


4. Open docs in browser:

```sh
$ browse ./docs/index.html
```

Or go straight to `webapi-parser` module doc:

```sh
$ browse ./docs/modules/_webapi_parser_.html
```
