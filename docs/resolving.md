---
---

# Resolving a "WebApi" Model
This document describes `webapi-parser` process called "resolution". This process is performed by syntax-specific `.resolve()` methods of all supported API syntaxes (RAML, OAS, AMF Graph). Resolving produces a "flat" document/model with all the references replaced by redundant copies of an actual data.

## Input and Output
Input to `.resolve()` is a [WebApi Model](https://raml-org.github.io/webapi-parser/js/classes/webapibaseunit.html) either not resolved or already resolved (nothing happens in this case).

Output is a resolved(/flat/explicit) WebApi Model with all the references replaced by definitions.

## Resolved model
A resolved WebApi Model has the following properties:

* All type expressions have been expanded
* All type names have been replaced by their expanded forms
* All nested types have an explicit type property that can be a built-in type name string or the declaration of a expanded type (single inheritance) or array of types (multiple inheritance)
* All facets with default values like `required` are made explicit
* All nullable property values have been replaced by unions
* All type properties have a String value
* All inheritance relationships have been resolved
* All the constraints defined for the type are valid
* Unions can only appear at the top level of the type form

### Examples

#### RAML 1.0
Unresolved

```raml
#%RAML 1.0
title: Albums API
version: v1

types:
  Song:
    properties:
      title: string
  Album:
    properties:
      title: string
      songs: Song[]
  ReleasedAlbum:
    type: Album

/released-albums:
  get:
    responses:
      200:
        body:
          application/json:
            type: ReleasedAlbum[]
```

Resolved
```raml
#%RAML 1.0
title: Albums API
version: v1
/released-albums:
  get:
    responses:
      "200":
        body:
          application/json:
            items:
              type: object
              additionalProperties: true
              properties:
                title:
                  type: string
                  required: true
                songs:
                  items:
                    additionalProperties: true
                    properties:
                      title:
                        type: string
                        required: true
                  required: true
```

#### OAS 2.0
Unresolved

```json
{
  "swagger": "2.0",
  "info": {
    "version": "1.0.0"
  },
  "host": "petstore.swagger.io",
  "basePath": "/v1",
  "paths": {
    "/pets": {
      "get": {
        "responses": {
          "200": {
            "description": "An paged array of pets",
            "schema": {
              "$ref": "#/definitions/Pets"
            }
          }
        }
      }
    }
  },
  "definitions": {
    "Pet": {
      "required": [
        "id",
        "name"
      ],
      "properties": {
        "id": {
          "type": "integer",
          "format": "int64"
        },
        "name": {
          "type": "string"
        },
        "tag": {
          "type": "string"
        }
      }
    },
    "Pets": {
      "type": "array",
      "items": {
        "$ref": "#/definitions/Pet"
      }
    }
  }
}
```

Resolved

```json
{
  "swagger": "2.0",
  "info": {
    "version": "1.0.0",
    "title": "API"
  },
  "host": "petstore.swagger.io",
  "basePath": "/v1",
  "paths": {
    "/pets": {
      "get": {
        "responses": {
          "200": {
            "description": "An paged array of pets",
            "schema": {
              "type": "array",
              "items": {
                "type": "object",
                "additionalProperties": true,
                "required": [
                  "id",
                  "name"
                ],
                "properties": {
                  "id": {
                    "type": "integer",
                    "format": "int64"
                  },
                  "name": {
                    "type": "string"
                  },
                  "tag": {
                    "type": "string"
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
```

## Preserving root definitions
When resolving a document it's possible to preserve root definitions like RAML 1.0 `types:`. This can be done by passing `true` boolean as the second argument to `.resolve()`. E.g. `.resolve(model, true)`.

Here's how RAML from the previous section would look like when resolved with preserving root definitions:

```raml
title: Albums API
version: v1
types:
  Song:
    additionalProperties: true
    properties:
      title:
        type: string
        required: true
  Album:
    additionalProperties: true
    properties:
      title:
        type: string
        required: true
      songs:
        items:
          additionalProperties: true
          properties:
            title:
              type: string
              required: true
        required: true
  ReleasedAlbum:
    type: object
    additionalProperties: true
    properties:
      title:
        type: string
        required: true
      songs:
        items:
          additionalProperties: true
          properties:
            title:
              type: string
              required: true
        required: true
/released-albums:
  get:
    responses:
      "200":
        body:
          application/json:
            items:
              type: object
              additionalProperties: true
              properties:
                title:
                  type: string
                  required: true
                songs:
                  items:
                    additionalProperties: true
                    properties:
                      title:
                        type: string
                        required: true
                  required: true
```
