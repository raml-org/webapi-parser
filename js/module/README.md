# Generating TypeDoc docs

Note: For this to work, [this PR](https://github.com/aml-org/amf/pull/411) needs to merged first because `amf-client-js` typings are incorrect.

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
$ ./node_modules/typedoc/bin/typedoc --out ./docs/ ./typings/webapi-parser.d.ts --includeDeclarations
```

4. Open docs in browser:

```sh
$ browse ./docs/index.html
```

Or go straight to `webapi-parser` module doc:

```sh
$ browse ./docs/modules/_typings_webapi_parser_d_.html
```

Or to `amf-client-js` docs:

```sh
$ browse ./docs/modules/_node_modules_amf_client_js_typings_amf_client_js_d_._amf_client_js_.html
```
